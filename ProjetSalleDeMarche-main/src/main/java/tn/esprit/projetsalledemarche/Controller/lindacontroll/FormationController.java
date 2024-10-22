package tn.esprit.projetsalledemarche.Controller.lindacontroll;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.projetsalledemarche.Entity.Linda.Formation;
import tn.esprit.projetsalledemarche.Service.Servicelinda.EmailService;
import tn.esprit.projetsalledemarche.Service.Servicelinda.FormationService;

import java.util.List;

@RestController
@RequestMapping("/formations")
public class FormationController {

    @Autowired
    private FormationService formationService;

    @Autowired
    private EmailService emailService; // Ajout de l'annotation @Autowired ici

    // Ajouter une formation
    @PostMapping("/ajouter")
    public ResponseEntity<Formation> ajouterFormation(@RequestBody Formation formation) {
        // Ajouter la formation
        Formation createdFormation = formationService.ajouterFormation(formation);

        // Envoyer un e-mail après l'ajout
        String destinataire = "mariem.ftouhi@esprit.tn"; // Remplacez par l'adresse e-mail du destinataire
        String sujet = "Nouvelle formation ajoutée";
        String contenu = "Une nouvelle formation intitulée '" + createdFormation.getTitre() + "' a été ajoutée.";

        try {
            // Appeler le service d'envoi d'e-mail
            emailService.envoyerEmail(destinataire, sujet, contenu);
        } catch (Exception e) {
            // Log the error message
            System.err.println("Erreur lors de l'envoi de l'e-mail : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null); // Renvoyer une réponse 500 Internal Server Error
        }

        // Retourner la réponse avec la formation créée
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFormation); // 201 Created
    }

    // Afficher toutes les formations
    @GetMapping("/afficher")
    public ResponseEntity<List<Formation>> afficherToutesFormations() {
        List<Formation> formations = formationService.afficherToutesFormations();
        return ResponseEntity.ok(formations); // 200 OK
    }

    // Modifier une formation
    @PutMapping("/modifier/{id}")
    public ResponseEntity<Formation> modifierFormation(@PathVariable Long id, @RequestBody Formation formation) {
        try {
            Formation updatedFormation = formationService.modifierFormation(id, formation);
            return ResponseEntity.ok(updatedFormation); // 200 OK
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build(); // 404 Not Found
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500 Internal Server Error
        }
    }

    // Supprimer une formation par ID
    @DeleteMapping("/supprimer/{id}")
    public ResponseEntity<Void> supprimerFormation(@PathVariable Long id) {
        try {
            formationService.supprimerFormation(id);
            return ResponseEntity.noContent().build(); // 204 No Content
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build(); // 404 Not Found
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500 Internal Server Error
        }
    }
    @GetMapping("/search")
    public ResponseEntity<List<Formation>> searchFormations(@RequestParam("keyword") String keyword) {
        List<Formation> formations = formationService.searchFormations(keyword);
        return ResponseEntity.ok(formations);
    }

    /**
     * Gets the total number of progressions for a specific formation.
     * @param formationId The ID of the formation.
     * @return The total number of progressions.
     */
    @GetMapping("/{formationId}/progressions/total")
    public ResponseEntity<Integer> getTotalProgressions(@PathVariable Long formationId) {
        int totalProgressions = formationService.getTotalProgressions(formationId);
        return ResponseEntity.ok(totalProgressions);
    }

}
