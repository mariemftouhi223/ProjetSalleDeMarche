package tn.esprit.projetsalledemarche.Service.Servicelinda;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.projetsalledemarche.Entity.Linda.Formation;
import tn.esprit.projetsalledemarche.Repository.lindarepo.FormationRepository;

import java.util.List;

@Service
public class FormationService implements IFormationService {
    @Autowired
    private FormationRepository formationRepository;

    /**
     * Ajoute une nouvelle formation à la base de données.
     * @param formation La formation à ajouter.
     * @return La formation ajoutée.
     */
    @Override
    public Formation ajouterFormation(Formation formation) {
        return formationRepository.save(formation);
    }

    /**
     * Récupère toutes les formations de la base de données.
     * @return Une liste de formations.
     */
    @Override
    public List<Formation> afficherToutesFormations() {
        return formationRepository.findAll();
    }

    /**
     * Modifie une formation existante dans la base de données.
     * @param id L'identifiant de la formation à modifier.
     * @param formation Les nouvelles informations de la formation.
     * @return La formation modifiée ou null si la formation n'existe pas.
     */
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
