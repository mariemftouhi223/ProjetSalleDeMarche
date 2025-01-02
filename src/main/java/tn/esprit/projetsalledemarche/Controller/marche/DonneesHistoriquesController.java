package tn.esprit.projetsalledemarche.Controller.marche;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.projetsalledemarche.Entity.Souha.DonneesHistoriques;
import tn.esprit.projetsalledemarche.Service.IMP.IDonneesHistoriqueService;

import java.util.List;

@RestController
@RequestMapping("/api/historiques")
public class DonneesHistoriquesController {
    @Autowired
    private IDonneesHistoriqueService service; // Injection de l'interface

    @GetMapping
    public List<DonneesHistoriques> getAllDonneesHistorique() {
        return service.getAllDonneesHistorique();
    }

    @GetMapping("/{id}")
    public DonneesHistoriques getDonneesHistoriqueById(@PathVariable Long id) {
        return service.getDonneesHistoriqueById(id);
    }

    @PostMapping
    public DonneesHistoriques createDonneesHistorique(@RequestBody DonneesHistoriques donneesHistorique) {
        return service.createDonneesHistorique(donneesHistorique);
    }



    @DeleteMapping("/{id}")
    public void deleteDonneesHistorique(@PathVariable Long id) {
        service.deleteDonneesHistorique(id);
    }
}
