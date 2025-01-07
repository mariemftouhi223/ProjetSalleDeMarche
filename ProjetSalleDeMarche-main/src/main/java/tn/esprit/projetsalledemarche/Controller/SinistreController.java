////
//// Source code recreated from a .class file by IntelliJ IDEA
//// (powered by FernFlower decompiler)
////
//
//package tn.esprit.projetsalledemarche.Controller;
//
//import java.math.BigDecimal;
//import java.util.Date;
//import java.util.List;
//import java.util.Map;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.format.annotation.DateTimeFormat;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import tn.esprit.projetsalledemarche.Entity.Sinistre;
//import tn.esprit.projetsalledemarche.Service.IProduitAssuranceService;
//import tn.esprit.projetsalledemarche.Service.IMP.ISinistreService;
//
//@RestController
//@RequestMapping({"/api"})
//@CrossOrigin({"*"})
//public class SinistreController {
//
//    @Autowired
//    ISinistreService SinistreService;
//    @Autowired
//    IProduitAssuranceService ProduitAssuranceService;
//
//    public SinistreController() {
//    }
//
//    @PostMapping({"/sinistre/ajout"})
//    Sinistre addSinistre(@RequestBody Sinistre sinistre) {
//        return this.SinistreService.addSinistre(sinistre);
//    }
//
//    @GetMapping({"/sinistre/{id}"})
//    Sinistre retrieveSinistre(@PathVariable("id") long idSinistre) {
//        return this.SinistreService.getSinistre(idSinistre);
//    }
//
//    @GetMapping({"/all"})
//    public ResponseEntity<List<Sinistre>> getAllSinistres() {
//        List<Sinistre> sinistres = this.SinistreService.getAllSinistres();
//        return sinistres != null && !sinistres.isEmpty() ? ResponseEntity.ok(sinistres) : ResponseEntity.notFound().build();
//    }
//
//    @DeleteMapping({"/sinistre/delete/{id}"})
//    void deleteSinistre(@PathVariable("id") Long idSinistre) {
//        this.SinistreService.deleteSinistre(idSinistre);
//    }
//
//    @PutMapping({"/sinistre/update"})
//    Sinistre updateSinistre(@RequestBody Sinistre sinistre) {
//        return this.SinistreService.updateSinistre(sinistre);
//    }
//
//    @PostMapping({"/ajouter"})
//    public ResponseEntity<?> createSinistre(@RequestParam String nomProduit, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateSinistre, @RequestParam BigDecimal montantSinistre, @RequestParam String etatSinistre) {
//        try {
//            Sinistre sinistre = this.SinistreService.createSinistre(nomProduit, dateSinistre, montantSinistre, etatSinistre);
//            return ResponseEntity.ok(sinistre);
//        } catch (RuntimeException var6) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", var6.getMessage()));
//        }
//    }
//
//    @GetMapping({"/date-range"})
//    public ResponseEntity<List<Sinistre>> getSinistresByDateRange(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
//        List<Sinistre> sinistres = this.SinistreService.getSinistresByDateRange(startDate, endDate);
//        return ResponseEntity.ok(sinistres);
//    }
//
//    @GetMapping({"/closed/date-range"})
//    public ResponseEntity<List<Sinistre>> getClosedSinistresByDateRange(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
//        List<Sinistre> sinistres = this.SinistreService.getClosedSinistresByDateRange(startDate, endDate);
//        return ResponseEntity.ok(sinistres);
//    }
//}
