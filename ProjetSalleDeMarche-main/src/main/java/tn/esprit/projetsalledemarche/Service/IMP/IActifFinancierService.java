package tn.esprit.projetsalledemarche.Service.IMP;

import tn.esprit.projetsalledemarche.Entity.Souha.ActifFinancier;

import java.util.List;

public interface IActifFinancierService {
    List<ActifFinancier> getAllActifs();
    ActifFinancier getActifById(Long id);
    ActifFinancier createActif(ActifFinancier actif);
    ActifFinancier updateActif(Long id, ActifFinancier actif);
    void deleteActif(Long id);
}
