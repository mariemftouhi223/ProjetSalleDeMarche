////
//// Source code recreated from a .class file by IntelliJ IDEA
//// (powered by FernFlower decompiler)
////
//
//package tn.esprit.projetsalledemarche.Service.IMP;
//
//import java.math.BigDecimal;
//import java.util.Date;
//import java.util.List;
//import tn.esprit.projetsalledemarche.Entity.ModeleActuariel;
//
//public interface IModeleActuarielService {
//    ModeleActuariel addModeleActuariel(ModeleActuariel modeleActuariel);
//
//    ModeleActuariel getModeleActuariels(long idModele);
//
//    List<ModeleActuariel> getAllModeleActuariels();
//
//    void deleteModeleActuariels(Long idModele);
//
//    ModeleActuariel updateModeleActuariels(ModeleActuariel modeleActuariel);
//
//    List<String[]> getPredictionsByAssetName(String nomActif);
//
//    String generatePredictionChart(String nomActif);
//
//    List<String[]> getPredictionsByAssetName1(String nomActif, Date dateCalcul);
//
//    BigDecimal calculerValeurEstimeeInitiale(String nomActif);
//
//    BigDecimal calculateValeurEstimee(String nomActif, Date dateCalcul, boolean saveToDatabase);
//
//    BigDecimal processAndSaveValeurEstimee(String nomActif, Date dateCalcul, boolean saveToDatabase);
//}
