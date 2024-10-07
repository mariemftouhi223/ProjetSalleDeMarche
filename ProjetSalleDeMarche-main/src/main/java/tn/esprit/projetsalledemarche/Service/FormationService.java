package tn.esprit.projetsalledemarche.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.projetsalledemarche.Entity.Formation;
import tn.esprit.projetsalledemarche.Repository.FormationRepository;

import java.util.List;
import java.util.Optional;

@Service
public class FormationService {
    @Autowired
    private FormationRepository formationRepository;
    public Formation saveFormation(Formation formation) {
        return formationRepository.save(formation);
    }

    // Get all Formations
    public List<Formation> getAllFormations() {
        return formationRepository.findAll();
    }

    // Get Formation by ID
    public Optional<Formation> getFormationById(Long id) {
        return formationRepository.findById(id);
    }

    // Delete Formation by ID
    public void deleteFormation(Long id) {
        formationRepository.deleteById(id);
    }

}
