package tn.esprit.projetsalledemarche.Controller;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import tn.esprit.projetsalledemarche.Entity.ProduitAssurance;
import tn.esprit.projetsalledemarche.Entity.Profil;
import tn.esprit.projetsalledemarche.Repository.ProfilRepository;
import tn.esprit.projetsalledemarche.Service.IProduitAssuranceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.projetsalledemarche.Service.IProfilService;
import tn.esprit.projetsalledemarche.Service.ProfilService;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class ProduitAssuranceController {

    @Autowired
    IProduitAssuranceService ProduitAssuranceService;


   // IProfilService ProfilService;

    @Autowired
    ProfilRepository profilRepository;

    @PostMapping("/produitassurance/ajout")
    ProduitAssurance addProduitAssurance(@RequestBody ProduitAssurance produitAssurance) {
        return ProduitAssuranceService.addProduitAssurance(produitAssurance);
    }

    @GetMapping("/produitassurance/{id}")
    ProduitAssurance retrieveProduitAssurance(@PathVariable("id") long idProduit) {
        return ProduitAssuranceService.getProduitAssurance(idProduit);
    }


    @GetMapping("/produitassurance")
    public ResponseEntity<List<ProduitAssurance>> getAllProduitsAssurance() {
        List<ProduitAssurance> produits = ProduitAssuranceService.getAllProduitAssurance();
        return ResponseEntity.ok(produits);
    }



    @DeleteMapping("/produitassurance/delete/{id}")
    void deleteProduitAssurance(@PathVariable("id") Long idProduit) {
        ProduitAssuranceService.deleteProduitAssurance(idProduit);
    }

    @PutMapping("/produitassurance/update")
    ProduitAssurance updateProduitAssurance(@RequestBody ProduitAssurance produitAssurance) {
        return ProduitAssuranceService.updateProduitAssurance(produitAssurance);
    }

    @GetMapping("/calculatePrime")
    public ResponseEntity<BigDecimal> getPrime(
            @RequestParam String nomActif,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateCalcul) {
        try {
            BigDecimal prime = ProduitAssuranceService.calculatePrime(nomActif, dateCalcul);
            return ResponseEntity.ok(prime);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    @GetMapping("/calculateCoverage")
    public ResponseEntity<BigDecimal> getCoverage(
            @RequestParam String nomActif,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateCalcul) {
        try {
            BigDecimal coverage = ProduitAssuranceService.calculateCoverage(nomActif, dateCalcul);
            return ResponseEntity.ok(coverage);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    @PostMapping("/generate")
    public ProduitAssurance generateProduitAssurance(
            @RequestParam String nomActif,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateCalcul,
            @RequestParam String typeAssurance,
            @RequestParam Long idProfil) {
        return ProduitAssuranceService.generateProduitAssurance(nomActif, dateCalcul, typeAssurance, idProfil);
    }
    @GetMapping("/sinistre-prime-ratio/{idProduit}")
    public ResponseEntity<Map<String, Object>> getSinistrePrimeRatio(@PathVariable Long idProduit) {
        try {
            Map<String, Object> response = ProduitAssuranceService.calculateSinistrePrimeRatio(idProduit);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/calculateSinistrePrimeRatio/clos")
    public ResponseEntity<Map<String, Object>> calculateSinistrePrimeRatioForClosSinistres(
            @RequestParam("nomProduit") String nomProduit) {
        if (nomProduit == null || nomProduit.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Le nom du produit est obligatoire."));
        }

        try {
            BigDecimal ratio = ProduitAssuranceService.calculateSinistrePrimeRatioForClosSinistres(nomProduit);

            Map<String, Object> response = Map.of(
                    "nomProduit", nomProduit,
                    "ratioSinistrePrimeClos", ratio
            );

            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }


    @GetMapping("/date-rangeP")
    public ResponseEntity<List<ProduitAssurance>> getProduitsByDateRange(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        List<ProduitAssurance> produits = ProduitAssuranceService.getProduitsByDateRange(startDate, endDate);
        return ResponseEntity.ok(produits);
    }



}
