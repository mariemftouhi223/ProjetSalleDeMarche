package tn.esprit.projetsalledemarche.Controller;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import tn.esprit.projetsalledemarche.Entity.Sinistre;
import tn.esprit.projetsalledemarche.Service.IProduitAssuranceService;
import tn.esprit.projetsalledemarche.Service.ISinistreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")

public class SinistreController {


    @Autowired
    ISinistreService SinistreService;
    @Autowired
    IProduitAssuranceService ProduitAssuranceService;


    @PostMapping("/sinistre/ajout")
    Sinistre addSinistre(@RequestBody Sinistre sinistre) {
        return SinistreService.addSinistre(sinistre);
    }

    @GetMapping("/sinistre/{id}")
    Sinistre retrieveSinistre(@PathVariable("id") long idSinistre) {
        return SinistreService.getSinistre(idSinistre);
    }


        @GetMapping("/all")
    public ResponseEntity<List<Sinistre>> getAllSinistres() {
        List<Sinistre> sinistres = SinistreService.getAllSinistres();
        if (sinistres != null && !sinistres.isEmpty()) {
            return ResponseEntity.ok(sinistres);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/sinistre/delete/{id}")
    void deleteSinistre(@PathVariable("id") Long idSinistre) {
        SinistreService.deleteSinistre(idSinistre);
    }

    @PutMapping("/sinistre/update")
    Sinistre updateSinistre(@RequestBody Sinistre sinistre) {
        return SinistreService.updateSinistre(sinistre);
    }



    @PostMapping("/ajouter")
    public ResponseEntity<?> createSinistre(
            @RequestParam String nomProduit,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateSinistre,
            @RequestParam BigDecimal montantSinistre,
            @RequestParam String etatSinistre) {

        try {
            Sinistre sinistre = SinistreService.createSinistre(nomProduit, dateSinistre, montantSinistre, etatSinistre);
            return ResponseEntity.ok(sinistre);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
    }


    @GetMapping("/date-range")
    public ResponseEntity<List<Sinistre>> getSinistresByDateRange(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        List<Sinistre> sinistres = SinistreService.getSinistresByDateRange(startDate, endDate);
        return ResponseEntity.ok(sinistres);
    }

    @GetMapping("/closed/date-range")
    public ResponseEntity<List<Sinistre>> getClosedSinistresByDateRange(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        List<Sinistre> sinistres = SinistreService.getClosedSinistresByDateRange(startDate, endDate);
        return ResponseEntity.ok(sinistres);
    }
}
