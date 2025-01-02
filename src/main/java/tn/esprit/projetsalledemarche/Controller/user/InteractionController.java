package tn.esprit.projetsalledemarche.Controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.projetsalledemarche.Entity.Linda.evenment.Interaction;

import tn.esprit.projetsalledemarche.Service.ser.event.InteractionService;

import java.util.List;

@RestController
@RequestMapping("/api/interactions")
public class InteractionController {

    private final InteractionService interactionService;

    @Autowired
    public InteractionController(InteractionService interactionService) {
        this.interactionService = interactionService;
    }

    // Endpoint pour "j'aime" sur un événement
    @PostMapping("/like")
    public ResponseEntity<Interaction> likeEvent(@RequestParam Long userId, @RequestParam Long eventId) {
        Interaction interaction = interactionService.likeEvent(userId, eventId);
        return ResponseEntity.ok(interaction);
    }

    // Endpoint pour ajouter un commentaire sur un événement
    @PostMapping("/commentaire")
    public ResponseEntity<Interaction> addComment(@RequestParam Long userId, @RequestParam Long eventId, @RequestParam String commentaire) {
        Interaction interaction = interactionService.saveComment(userId, eventId, commentaire);
        return ResponseEntity.ok(interaction);
    }

    // Endpoint pour sauvegarder un événement
    @PostMapping("/sauvegarder")
    public ResponseEntity<Interaction> saveEvent(@RequestParam Long userId, @RequestParam Long eventId) {
        Interaction interaction = interactionService.saveEvent(userId, eventId);
        return ResponseEntity.ok(interaction);
    }

    // API pour obtenir les interactions d'un utilisateur
    @GetMapping("/users/{userId}")
    public ResponseEntity<List<Interaction>> getInteractionsByUserId(@PathVariable Long userId) {
        List<Interaction> interactions = interactionService.findByUserId(userId);
        return ResponseEntity.ok(interactions);
    }
}
