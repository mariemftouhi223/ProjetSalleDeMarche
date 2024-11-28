package tn.esprit.projetsalledemarche.Controller.formation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import tn.esprit.projetsalledemarche.Entity.Linda.formation.Progression;
import tn.esprit.projetsalledemarche.Service.Servicelinda.ser.formation.ProgressionService;

import java.util.List;

@RestController
@RequestMapping("/progressions")
@Component
public class ProgressionController {
    @Autowired
    ProgressionService progressionService;
    // Endpoint pour obtenir la durée totale de formation en jours pour une progression spécifique
    @GetMapping("/{progressionId}/duration")
    public ResponseEntity<Long> getTrainingDuration(@PathVariable Long progressionId) {
        long duration = progressionService.calculateTrainingDuration(progressionId);
        return ResponseEntity.ok(duration);
    }
   // @GetMapping("/{progressionId}/status")
    //public ResponseEntity<ProgressionStatus> getProgressionStatus(@PathVariable Long progressionId) {
       // Progression progression = progressionService.getProgressionById(progressionId); // Méthode à ajouter dans ProgressionService pour récupérer une progression par ID
        //return ResponseEntity.ok(progression.getStatus());
    //}

    // Endpoint pour mettre à jour le statut d'une progression
    @PutMapping("/{progressionId}/update-status")
    public ResponseEntity<Progression> updateProgressionStatus(@PathVariable Long progressionId) {
        Progression progression = progressionService.getProgressionById(progressionId);
        progressionService.updateProgressionStatus(progression);
        return ResponseEntity.ok(progression);
    }
    @GetMapping
    public ResponseEntity<List<Progression>> getAllProgressions() {
        List<Progression> progressions = progressionService.getAllProgressions();
        return ResponseEntity.ok(progressions);
    }
}
