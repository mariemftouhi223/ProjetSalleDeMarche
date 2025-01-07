////
//// Source code recreated from a .class file by IntelliJ IDEA
//// (powered by FernFlower decompiler)
////
//
//package tn.esprit.projetsalledemarche.Service;
//
//import java.math.BigDecimal;
//import java.util.Date;
//import java.util.List;
//import java.util.Map;
//import tn.esprit.projetsalledemarche.Entity.ProduitAssurance;
//
//public interface IProduitAssuranceService {
//    ProduitAssurance getProduitAssurance(long idProduit);
//
//    void deleteProduitAssurance(Long idProduit);
//
//    ProduitAssurance updateProduitAssurance(ProduitAssurance produitAssurance);
//
//    List<ProduitAssurance> getAllProduitAssurance();
//
//    ProduitAssurance addProduitAssurance(ProduitAssurance produitAssurance);
//
//    BigDecimal calculatePrime(String nomActif, Date dateCalcul);
//
//    BigDecimal calculateCoverage(String nomActif, Date dateCalcul);
//
//    ProduitAssurance generateProduitAssurance(String nomActif, Date dateCalcul, String typeAssurance, Long IdProfil);
//
//    Map<String, Object> calculateSinistrePrimeRatio(Long idProduit);
//
//    BigDecimal calculateSinistrePrimeRatioForClosSinistres(String nomProduit);
//
//    List<ProduitAssurance> getProduitsByDateRange(Date startDate, Date endDate);
//}
