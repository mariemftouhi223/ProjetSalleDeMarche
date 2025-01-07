package tn.esprit.projetsalledemarche.Controller.marche;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.projetsalledemarche.Entity.Souha.ActifFinancier;
import tn.esprit.projetsalledemarche.Entity.Souha.DonneesHistoriques;
import tn.esprit.projetsalledemarche.Service.ser.marche.ActifFinancierService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.http.MediaType;

import java.util.List;
@RestController
@RequestMapping("/api/actifs")
public class ActifFinancierController {

    @Autowired
    private ActifFinancierService service;

    // Récupérer tous les actifs financiers
    @GetMapping
    public List<ActifFinancier> getAllActifs() {
        return service.getAllActifs();
    }

    // Récupérer un actif par ID
    @GetMapping("/{id}")
    public ActifFinancier getActifById(@PathVariable Long id) {
        return service.getActifById(id);
    }
    @GetMapping("/{id}/historiques")
    public List<DonneesHistoriques> getHistoriqueForActif(@PathVariable Long id) {
        return service.getHistoriqueForActif(id);  // Assuming you have this service method
    }
    // Ajouter un nouvel actif
    @PostMapping
    public ActifFinancier createActif(@RequestBody ActifFinancier actif) {
        return service.createActif(actif);
    }

    // Mettre à jour un actif existant
    @PutMapping("/{id}")
    public ActifFinancier updateActif(@PathVariable Long id, @RequestBody ActifFinancier actif) {
        return service.updateActif(id, actif);
    }

    // Supprimer un actif financier
    @DeleteMapping("/{id}")
    public void deleteActif(@PathVariable Long id) {
        service.deleteActif(id);
    }
}
