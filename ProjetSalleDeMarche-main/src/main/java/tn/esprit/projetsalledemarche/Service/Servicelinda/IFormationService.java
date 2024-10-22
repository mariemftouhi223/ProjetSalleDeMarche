package tn.esprit.projetsalledemarche.Service.Servicelinda;

import tn.esprit.projetsalledemarche.Entity.Linda.Formation;

import java.util.List;

public interface IFormationService {
    Formation ajouterFormation(Formation formation);
    List<Formation> afficherToutesFormations();
    Formation modifierFormation(Long id, Formation formation);
    void supprimerFormation(Long idFormation);
    List<Formation> searchFormations(String keyword);
    int getTotalProgressions(Long formationId);
}
