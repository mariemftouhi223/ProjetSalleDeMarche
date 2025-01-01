package tn.esprit.projetsalledemarche.Controller.user;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import tn.esprit.projetsalledemarche.Entity.Linda.formation.UserInteraction;

import tn.esprit.projetsalledemarche.Entity.Linda.user.Formation;
import tn.esprit.projetsalledemarche.Service.user.UserInteractionService;

import java.util.List;

@RestController
@RequestMapping("/api/recommendations")
public class UserInteractionController {
    @Autowired
    private UserInteractionService userInteractionService;

    @PostMapping
    public UserInteraction createInteraction(@RequestBody UserInteraction interaction) {
        return userInteractionService.saveInteraction(interaction);
    }

    // Endpoint pour récupérer toutes les interactions
    @GetMapping
    public List<UserInteraction> getAllInteractions() {
        return userInteractionService.getAllInteractions();
    }

    // Endpoint pour obtenir les recommandations de formations pour un utilisateur spécifique
    @GetMapping("/{userId}")
    public List<Formation> getRecommendations(@PathVariable Long userId) {
        return userInteractionService.getRecommendedFormations(userId);
    }
}
