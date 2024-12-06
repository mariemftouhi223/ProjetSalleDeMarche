package tn.esprit.projetsalledemarche.Service;

import tn.esprit.projetsalledemarche.Entity.ModeleActuariel;
import tn.esprit.projetsalledemarche.Entity.ProduitAssurance;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface IModeleActuarielService {

    ModeleActuariel addModeleActuariel(ModeleActuariel modeleActuariel);

    ModeleActuariel getModeleActuariels(long idModele);

    List<ModeleActuariel> getAllModeleActuariels();

    void deleteModeleActuariels(Long idModele);

    ModeleActuariel updateModeleActuariels(ModeleActuariel modeleActuariel);



     List<String[]> getPredictionsByAssetName(String nomActif);
     String generatePredictionChart(String nomActif);
    List<String[]> getPredictionsByAssetName1(String nomActif , Date dateCalcul);
     BigDecimal calculerValeurEstimeeInitiale(String nomActif);
     BigDecimal calculateValeurEstimee(String nomActif, Date dateCalcul, boolean saveToDatabase);
    BigDecimal processAndSaveValeurEstimee(String nomActif, Date dateCalcul, boolean saveToDatabase);
}
