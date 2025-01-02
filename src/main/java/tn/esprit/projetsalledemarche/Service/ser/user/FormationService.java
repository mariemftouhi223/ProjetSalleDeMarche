package tn.esprit.projetsalledemarche.Service.ser.user;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.projetsalledemarche.Entity.Linda.user.Formation;
import tn.esprit.projetsalledemarche.Repository.lindarepo.formation.FormationRepository;

import tn.esprit.projetsalledemarche.Service.IMP.IFormationService;

import java.util.List;


@Service
public class FormationService implements IFormationService {
    @Autowired
    private FormationRepository formationRepository;

    @Override
    public Formation ajouterFormation(Formation formation) {
        return formationRepository.save(formation);
    }


    @Override
    public List<Formation> afficherToutesFormations() {
        return formationRepository.findAll();
    }
    public Formation afficherFormationParId(Long id) {
        return formationRepository.findById(id).orElse(null);
    }


    @Override
    public Formation modifierFormation(Long id, Formation formation) {
        Formation existingFormation = formationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Formation not found with id " + id));

        existingFormation.setTitre(formation.getTitre());
        existingFormation.setDescription(formation.getDescription());
        existingFormation.setDateCreation(formation.getDateCreation());

        return formationRepository.save(existingFormation);
    }

    /**
     * Supprime une formation par ID.
     * @param idFormation L'identifiant de la formation à supprimer.
     */
    @Override
    public void supprimerFormation(Long idFormation) {
        formationRepository.deleteById(idFormation);
    }
    // Search formations by title or description
    public List<Formation> searchFormations(String keyword) {
        return formationRepository.searchFormations(keyword);
    }
    public int getTotalProgressions(Long formationId) {
        Formation formation = formationRepository.findById(formationId)
                .orElseThrow(() -> new EntityNotFoundException("Formation not found"));
        return formation.getProgressions().size();
    }

}
