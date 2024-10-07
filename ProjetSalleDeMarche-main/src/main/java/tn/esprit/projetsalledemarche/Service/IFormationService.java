package tn.esprit.projetsalledemarche.Service;

import tn.esprit.projetsalledemarche.Entity.Formation;

import java.util.List;
import java.util.Optional;

public interface IFormationService {
    Formation saveFormation(Formation formation);

    // Obtenir toutes les formations
    List<Formation> getAllFormations();

    // Obtenir une formation par son ID
    Optional<Formation> getFormationById(Long id);

    // Supprimer une formation par son ID
    void deleteFormation(Long id);
}
