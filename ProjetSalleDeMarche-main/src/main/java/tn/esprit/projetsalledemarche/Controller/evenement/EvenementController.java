package tn.esprit.projetsalledemarche.Controller.evenement;


import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.projetsalledemarche.Entity.Linda.evenment.Evenement;

import tn.esprit.projetsalledemarche.Service.event.EvenementService;

import tn.esprit.projetsalledemarche.Service.event.FinanceService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/evenements")
public class EvenementController {


    @Autowired
    private EvenementService evenementService;

    private FinanceService financeService;
    // Ajouter un événement
    @PostMapping("/ajouter")
    public Evenement ajouterEvenement(@RequestBody Evenement evenement) {
        return evenementService.ajouterEvenement(evenement);
    }
    @GetMapping("/afficher/{id}")
    public ResponseEntity<Evenement> afficherEvenementParId(@PathVariable Long id) {
        Evenement evenement = evenementService.afficherevenementParId(id);
        if (evenement != null) {
            return ResponseEntity.ok(evenement); // 200 OK
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404 Not Found si l'ID n'existe pas
        }
    }

    // Afficher tous les événements
    @GetMapping("/afficher")
    public List<Evenement> afficherTousEvenements() {
        return evenementService.afficherTousEvenements();
    }

    // Modifier un événement
    @PutMapping("/modifier/{id}")
    public Evenement modifierEvenement(@PathVariable("id") Long id, @RequestBody Evenement evenement) {
        return evenementService.modifierEvenement(id, evenement);
    }

    @DeleteMapping("/supprimer/{idEvenement}")
    public ResponseEntity<String> supprimerEvenement(@PathVariable Long idEvenement) {
        try {
            evenementService.supprimerEvenement(idEvenement);
            return ResponseEntity.ok("{\"message\": \"Événement supprimé avec succès.\"}"); // 200 OK
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\": \"Événement non trouvé.\"}"); // 404 Not Found
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\": \"Erreur lors de la suppression : " + e.getMessage() + "\"}"); // 500 Internal Server Error
        }
    }



    @Autowired
    public EvenementController(FinanceService financeService) {
        this.financeService = financeService;
    }

    @GetMapping("/stock/{symbol}")
    public String getStockData(@PathVariable String symbol) {
        return financeService.getStockData(symbol);
    }



    private double impactToDouble(String impact) {
        return switch (impact.toLowerCase()) {
            case "faible" -> 0.0;
            case "moyen" -> 1.0;
            case "élevé" -> 2.0;
            default -> 0.0;
        };
    }





    @PostMapping("/inscrire/{idEvenement}/{idUtilisateur}")
    public ResponseEntity<Map<String, Object>> inscrireAEvent(@PathVariable Long idEvenement, @PathVariable Long idUtilisateur) {
        try {
            // Appeler le service pour inscrire l'utilisateur à l'événement
            String resultat = evenementService.inscrireUtilisateurAEvent(idEvenement, idUtilisateur);

            // Créer un objet JSON pour la réponse avec des valeurs homogènes (Map<String, Object>)
            Map<String, Object> response = new HashMap<>();
            response.put("message", resultat); // Message de réussite
            response.put("idUtilisateur", idUtilisateur); // ID de l'utilisateur
            response.put("idEvenement", idEvenement); // ID de l'événement

            // Retourner une réponse avec un code 200 OK
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // Renvoyer un message d'erreur en cas de problème interne
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Erreur lors de l'inscription : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse); // 500 Internal Server Error
        }
    }




}

