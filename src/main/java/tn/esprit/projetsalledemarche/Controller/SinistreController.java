package tn.esprit.projetsalledemarche.Controller;
import org.springframework.format.annotation.DateTimeFormat;

import org.springframework.http.HttpStatus;
import tn.esprit.projetsalledemarche.Entity.ProduitAssurance;
import tn.esprit.projetsalledemarche.Entity.Sinistre;
import tn.esprit.projetsalledemarche.Entity.SinistreDTO;
import tn.esprit.projetsalledemarche.Repository.ProduitAssuranceRepository;
import tn.esprit.projetsalledemarche.Service.IProduitAssuranceService;
import tn.esprit.projetsalledemarche.Service.ISinistreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
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
    @Autowired
    ProduitAssuranceRepository produitAssuranceRepository;


    @PostMapping("/sinistre/ajout")
    Sinistre addSinistre(@RequestBody Sinistre sinistre) {
        return SinistreService.addSinistre(sinistre);
    }

    @GetMapping("/sinistre/{id}")
    Sinistre retrieveSinistre(@PathVariable("id") long idSinistre) {
        return SinistreService.getSinistre(idSinistre);
    }
    @GetMapping("/sinistreDTO/{id}")
    SinistreDTO retrieveSinistreDTO(@PathVariable("id") long idSinistre) {
        return SinistreService.getSinistreDTO(idSinistre);
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

    @PutMapping("/sinistre/update/{id}")
    public ResponseEntity<?> updateSinistre(
            @PathVariable("id") Long idSinistre,
            @RequestBody Map<String, Object> updates) {
        try {
            // Rechercher le sinistre par ID
            Sinistre sinistre = SinistreService.getSinistre(idSinistre);
            if (sinistre == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Sinistre introuvable pour l'ID : " + idSinistre));
            }

            // Log des données reçues
            System.out.println("Données reçues pour mise à jour : " + updates);

            // Mettre à jour les champs du sinistre
            if (updates.containsKey("dateSinistre")) {
                sinistre.setDateSinistre(new SimpleDateFormat("yyyy-MM-dd").parse((String) updates.get("dateSinistre")));
            }
            if (updates.containsKey("montantSinistre")) {
                sinistre.setMontantSinistre(new BigDecimal((String) updates.get("montantSinistre")));
            }
            if (updates.containsKey("etatSinistre")) {
                sinistre.setEtatSinistre((String) updates.get("etatSinistre"));
            }

            // Vérifier si un nom de produit d'assurance est fourni
            if (updates.containsKey("nomProduitAssurance")) {
                String nomProduitAssurance = (String) updates.get("nomProduitAssurance");

                // Rechercher le produit d'assurance par son nom
                List<ProduitAssurance> produitsAssurance = produitAssuranceRepository.findByNomProduit(nomProduitAssurance);
                if (produitsAssurance == null || produitsAssurance.isEmpty()) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body(Map.of("error", "Produit Assurance introuvable pour le nom : " + nomProduitAssurance));
                }

                if (produitsAssurance.size() > 1) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(Map.of("error", "Plusieurs produits d'assurance trouvés pour le nom : " + nomProduitAssurance));
                }

                // Associer le produit d'assurance au sinistre
                ProduitAssurance produitAssurance = produitsAssurance.get(0);
                sinistre.setProduitAssurance(produitAssurance);
            }

            // Sauvegarder les modifications
            Sinistre updatedSinistre = SinistreService.updateSinistre(sinistre);
            return ResponseEntity.ok(updatedSinistre);

        } catch (Exception e) {
            e.printStackTrace(); // Affiche la pile d'erreurs dans les logs
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Erreur lors de la mise à jour : " + e.getMessage()));
        }
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
