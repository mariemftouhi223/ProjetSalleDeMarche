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
    private EmailService emailService;

    // Ajouter une formation
    @PostMapping("/ajouter")
    public ResponseEntity<Formation> ajouterFormation(@RequestBody Formation formation) {
        System.out.println("Ajout de la formation : " + formation);

        try {
            Formation createdFormation = formationService.ajouterFormation(formation);
            System.out.println("Formation ajoutée : " + createdFormation);

            // Envoyer un e-mail après l'ajout
            String destinataire = "mariem.ftouhi@esprit.tn";
            String sujet = "Nouvelle formation ajoutée";
            String contenu = "Une nouvelle formation intitulée '" + createdFormation.getTitre() + "' a été ajoutée.";

            emailService.envoyerEmail(destinataire, sujet, contenu);

            return ResponseEntity.status(HttpStatus.CREATED).body(createdFormation); // 201 Created
        } catch (Exception e) {
            System.err.println("Erreur lors de l'ajout de la formation : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500 Internal Server Error
        }
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
            System.err.println("Erreur lors de la modification de la formation : " + e.getMessage());
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
            System.err.println("Erreur lors de la suppression de la formation : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500 Internal Server Error
        }
    }

    // Rechercher des formations par mot-clé
    @GetMapping("/search")
    public ResponseEntity<List<Formation>> searchFormations(@RequestParam("keyword") String keyword) {
        List<Formation> formations = formationService.searchFormations(keyword);
        return ResponseEntity.ok(formations);
    }

    // Obtenir le total des progressions pour une formation spécifique
    @GetMapping("/{formationId}/progressions/total")
    public ResponseEntity<Integer> getTotalProgressions(@PathVariable Long formationId) {
        try {
            int totalProgressions = formationService.getTotalProgressions(formationId);
            return ResponseEntity.ok(totalProgressions); // 200 OK
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build(); // 404 Not Found
        } catch (Exception e) {
            System.err.println("Erreur lors de l'obtention du total des progressions : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500 Internal Server Error
        }
    }
}
