package tn.esprit.projetsalledemarche.Service.ser.marche;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.projetsalledemarche.Entity.Souha.DonneesHistoriques;
import tn.esprit.projetsalledemarche.Repository.souharepo.DonneesHistoriqueRepository;
import tn.esprit.projetsalledemarche.Service.IMP.IDonneesHistoriqueService;

import java.util.List;
@Service
public class DonneesHistoriquesService implements IDonneesHistoriqueService {
    @Autowired
    private DonneesHistoriqueRepository repository;

    @Override
    public List<DonneesHistoriques> getAllDonneesHistorique() {
        return repository.findAll();
    }

    @Override
    public DonneesHistoriques getDonneesHistoriqueById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Données historiques introuvables avec ID : " + id));
    }

    @Override
    public DonneesHistoriques createDonneesHistorique(DonneesHistoriques donneesHistorique) {
        return repository.save(donneesHistorique);
    }



    @Override
    public void deleteDonneesHistorique(Long id) {
        repository.deleteById(id);
    }

}
