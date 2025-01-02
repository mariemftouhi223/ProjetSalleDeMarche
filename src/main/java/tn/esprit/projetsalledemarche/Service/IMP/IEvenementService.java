package tn.esprit.projetsalledemarche.Service.IMP;

import tn.esprit.projetsalledemarche.Entity.Linda.evenment.Evenement;

import java.util.List;

public interface IEvenementService {
    Evenement ajouterEvenement(Evenement evenement);
    List<Evenement> afficherTousEvenements();
    Evenement modifierEvenement(Long id, Evenement evenement);
    void supprimerEvenement(Long id);



}
