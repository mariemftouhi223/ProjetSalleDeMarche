//package tn.esprit.projetsalledemarche.Controller;
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
//import tn.esprit.projetsalledemarche.Entity.ProduitAssurance;
//import tn.esprit.projetsalledemarche.Repository.ProfilRepository;
//import tn.esprit.projetsalledemarche.Service.IProduitAssuranceService;
//
//@RestController
//@RequestMapping({"/api"})
//@CrossOrigin({"*"})
//public class ProduitAssuranceController {
//    @Autowired
//    IProduitAssuranceService ProduitAssuranceService;
//    @Autowired
//    ProfilRepository profilRepository;
//
//    public ProduitAssuranceController() {
//    }
//
//    @PostMapping({"/produitassurance/ajout"})
//    ProduitAssurance addProduitAssurance(@RequestBody ProduitAssurance produitAssurance) {
//        return this.ProduitAssuranceService.addProduitAssurance(produitAssurance);
//    }
//
//    @GetMapping({"/produitassurance/{id}"})
//    ProduitAssurance retrieveProduitAssurance(@PathVariable("id") long idProduit) {
//        return this.ProduitAssuranceService.getProduitAssurance(idProduit);
//    }
//
//    @GetMapping({"/produitassurance"})
//    public ResponseEntity<List<ProduitAssurance>> getAllProduitsAssurance() {
//        List<ProduitAssurance> produits = this.ProduitAssuranceService.getAllProduitAssurance();
//        return ResponseEntity.ok(produits);
//    }
//
//    @DeleteMapping({"/produitassurance/delete/{id}"})
//    void deleteProduitAssurance(@PathVariable("id") Long idProduit) {
//        this.ProduitAssuranceService.deleteProduitAssurance(idProduit);
//    }
//
//    @PutMapping({"/produitassurance/update"})
//    ProduitAssurance updateProduitAssurance(@RequestBody ProduitAssurance produitAssurance) {
//        return this.ProduitAssuranceService.updateProduitAssurance(produitAssurance);
//    }
//
//    @GetMapping({"/calculatePrime"})
//    public ResponseEntity<BigDecimal> getPrime(@RequestParam String nomActif, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateCalcul) {
//        try {
//            BigDecimal prime = this.ProduitAssuranceService.calculatePrime(nomActif, dateCalcul);
//            return ResponseEntity.ok(prime);
//        } catch (RuntimeException var4) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body((Object)null);
//        }
//    }
//
//    @GetMapping({"/calculateCoverage"})
//    public ResponseEntity<BigDecimal> getCoverage(@RequestParam String nomActif, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateCalcul) {
//        try {
//            BigDecimal coverage = this.ProduitAssuranceService.calculateCoverage(nomActif, dateCalcul);
//            return ResponseEntity.ok(coverage);
//        } catch (RuntimeException var4) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body((Object)null);
//        }
//    }
//
//    @PostMapping({"/generate"})
//    public ProduitAssurance generateProduitAssurance(@RequestParam String nomActif, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateCalcul, @RequestParam String typeAssurance, @RequestParam Long idProfil) {
//        return this.ProduitAssuranceService.generateProduitAssurance(nomActif, dateCalcul, typeAssurance, idProfil);
//    }
//
//    @GetMapping({"/sinistre-prime-ratio/{idProduit}"})
//    public ResponseEntity<Map<String, Object>> getSinistrePrimeRatio(@PathVariable Long idProduit) {
//        try {
//            Map<String, Object> response = this.ProduitAssuranceService.calculateSinistrePrimeRatio(idProduit);
//            return ResponseEntity.ok(response);
//        } catch (RuntimeException var3) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", var3.getMessage()));
//        }
//    }
//
//    @GetMapping({"/calculateSinistrePrimeRatio/clos"})
//    public ResponseEntity<Map<String, Object>> calculateSinistrePrimeRatioForClosSinistres(@RequestParam("nomProduit") String nomProduit) {
//        if (nomProduit != null && !nomProduit.isEmpty()) {
//            try {
//                BigDecimal ratio = this.ProduitAssuranceService.calculateSinistrePrimeRatioForClosSinistres(nomProduit);
//                Map<String, Object> response = Map.of("nomProduit", nomProduit, "ratioSinistrePrimeClos", ratio);
//                return ResponseEntity.ok(response);
//            } catch (RuntimeException var4) {
//                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", var4.getMessage()));
//            }
//        } else {
//            return ResponseEntity.badRequest().body(Map.of("error", "Le nom du produit est obligatoire."));
//        }
//    }
//
//    @GetMapping({"/date-rangeP"})
//    public ResponseEntity<List<ProduitAssurance>> getProduitsByDateRange(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
//        List<ProduitAssurance> produits = this.ProduitAssuranceService.getProduitsByDateRange(startDate, endDate);
//        return ResponseEntity.ok(produits);
//    }
//}
//
