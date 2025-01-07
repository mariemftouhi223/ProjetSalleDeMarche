package tn.esprit.projetsalledemarche.Service.ser.marche;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.projetsalledemarche.Entity.Souha.ActifFinancier;
import tn.esprit.projetsalledemarche.Entity.Souha.DonneesHistoriques;
import tn.esprit.projetsalledemarche.Repository.souharepo.ActifFinancierRepository;
import tn.esprit.projetsalledemarche.Repository.souharepo.DonneesHistoriqueRepository;
import tn.esprit.projetsalledemarche.Service.IMP.IActifFinancierService;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
@Service
public class ActifFinancierService implements IActifFinancierService {

    @Autowired
    private ActifFinancierRepository repository;
    @Autowired
    private DonneesHistoriqueRepository donneesHistoriquesRepository;

    // Method to get historical data for a specific ActifFinancier
    public List<DonneesHistoriques> getHistoriqueForActif(Long actifId) {
        // Retrieve the ActifFinancier by its ID
        Optional<ActifFinancier> actifOpt = repository.findById(actifId);
        if (actifOpt.isPresent()) {
            // If Actif exists, fetch its historical data (donneesHistoriques)
            return actifOpt.get().getHistorique();
        }
        return null; // Return null if Actif not found (you could handle this with an exception if preferred)
    }
    // Récupérer tous les actifs financiers
    public List<ActifFinancier> getAllActifs() {
        return repository.findAll();
    }

    // Récupérer un actif financier par son ID
    public ActifFinancier getActifById(Long id) {
        return repository.findById(id)
                .orElseThrow(new Supplier<RuntimeException>() {
                    @Override
                    public RuntimeException get() {
                        return new RuntimeException("Actif introuvable avec ID : " );
                    }
                });
    }

    // Ajouter un nouvel actif financier
    public ActifFinancier createActif(ActifFinancier actif) {
        return repository.save(actif);
    }

    // Mettre à jour un actif existant
    public ActifFinancier updateActif(Long id, ActifFinancier actif) {
        ActifFinancier existing = getActifById(id);
        existing.setNomActif(actif.getNomActif());
        existing.setValeurActuelle(actif.getValeurActuelle());
        existing.setTypeActif(actif.getTypeActif());
        existing.setMarche(actif.getMarche());
        return repository.save(existing);
    }

    // Supprimer un actif financier
    public void deleteActif(Long id) {
        repository.deleteById(id);
    }
}
