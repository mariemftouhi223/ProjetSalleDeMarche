package tn.esprit.projetsalledemarche.Service.IMP;

import tn.esprit.projetsalledemarche.Entity.Linda.user.Formation;

import java.util.List;

public interface IFormationService {
    Formation ajouterFormation(Formation formation);
    List<Formation> afficherToutesFormations();
    Formation modifierFormation(Long id, Formation formation);
    void supprimerFormation(Long idFormation);
    List<Formation> searchFormations(String keyword);
    int getTotalProgressions(Long formationId);
}
