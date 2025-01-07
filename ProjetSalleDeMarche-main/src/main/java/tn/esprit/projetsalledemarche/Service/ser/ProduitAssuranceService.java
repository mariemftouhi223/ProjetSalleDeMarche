////
//// Source code recreated from a .class file by IntelliJ IDEA
//// (powered by FernFlower decompiler)
////
//
//package tn.esprit.projetsalledemarche.Service.ser;
//
//import java.math.BigDecimal;
//import java.math.RoundingMode;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Objects;
//import java.util.Set;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import tn.esprit.projetsalledemarche.Entity.ProduitAssurance;
//import tn.esprit.projetsalledemarche.Entity.Profil;
//import tn.esprit.projetsalledemarche.Entity.Sinistre;
//import tn.esprit.projetsalledemarche.Entity.TypeAssurance;
//import tn.esprit.projetsalledemarche.Repository.ProduitAssuranceRepository;
//import tn.esprit.projetsalledemarche.Repository.ProfilRepository;
//import tn.esprit.projetsalledemarche.Service.ser.ModeleActuarielService;
//
//@Service
//public class ProduitAssuranceService implements tn.esprit.projetsalledemarche.Service.IProduitAssuranceService {
//    @Autowired
//    private ProduitAssuranceRepository produitAssuranceRepository;
//    @Autowired
//    private ModeleActuarielService modeleActuarielService;
//    @Autowired
//    private ProfilRepository profilRepository;
//
//    public ProduitAssuranceService() {
//    }
//
//    public ProduitAssurance getProduitAssurance(long idProduit) {
//        return (ProduitAssurance)this.produitAssuranceRepository.findById(idProduit).orElse((Object)null);
//    }
//
//    public void deleteProduitAssurance(Long idProduit) {
//        this.produitAssuranceRepository.deleteById(idProduit);
//    }
//
//    public ProduitAssurance updateProduitAssurance(ProduitAssurance produitAssurance) {
//        return (ProduitAssurance)this.produitAssuranceRepository.save(produitAssurance);
//    }
//
//    public List<ProduitAssurance> getAllProduitAssurance() {
//        return this.produitAssuranceRepository.findAll();
//    }
//
//    public ProduitAssurance addProduitAssurance(ProduitAssurance produitAssurance) {
//        return (ProduitAssurance)this.produitAssuranceRepository.save(produitAssurance);
//    }
//
//    public BigDecimal calculatePrime(String nomActif, Date dateCalcul) {
//        List<Double> predictions = this.modeleActuarielService.getPredictionsAroundDate(nomActif, dateCalcul);
//        if (predictions.isEmpty()) {
//            throw new RuntimeException("Aucune prédiction trouvée pour l'actif : " + nomActif);
//        } else {
//            double moyenne = predictions.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
//            double prime = moyenne * 0.01 + 50.0 + moyenne * 0.1;
//            return BigDecimal.valueOf(prime);
//        }
//    }
//
//    public BigDecimal calculateCoverage(String nomActif, Date dateCalcul) {
//        List<Double> predictions = this.modeleActuarielService.getPredictionsAroundDate(nomActif, dateCalcul);
//        if (predictions.isEmpty()) {
//            throw new RuntimeException("Aucune prédiction trouvée pour l'actif : " + nomActif);
//        } else {
//            double maxPrediction = predictions.stream().mapToDouble(Double::doubleValue).max().orElse(0.0);
//            double couverture = maxPrediction * 3.0;
//            return BigDecimal.valueOf(couverture);
//        }
//    }
//
//    public ProduitAssurance generateProduitAssurance(String nomActif, Date dateCalcul, String typeAssurance, Long idProfil) {
//        if (nomActif != null && !nomActif.isEmpty()) {
//            if (dateCalcul == null) {
//                throw new IllegalArgumentException("La date de calcul ne peut pas être null.");
//            } else if (typeAssurance != null && !typeAssurance.isEmpty()) {
//                if (idProfil == null) {
//                    throw new IllegalArgumentException("L'ID du profil ne peut pas être null.");
//                } else {
//                    Profil profil = (Profil)this.profilRepository.findById(idProfil).orElseThrow(() -> {
//                        return new RuntimeException("Profil utilisateur introuvable pour l'ID : " + idProfil);
//                    });
//                    BigDecimal prime = this.calculatePrime(nomActif, dateCalcul);
//                    BigDecimal couverture = this.calculateCoverage(nomActif, dateCalcul);
//                    ProduitAssurance produitAssurance = new ProduitAssurance();
//                    produitAssurance.setNomProduit("Assurance " + nomActif);
//                    produitAssurance.setPrime(prime);
//                    produitAssurance.setCouverture(couverture);
//                    produitAssurance.setAtype(TypeAssurance.valueOf(typeAssurance.toUpperCase()));
//                    produitAssurance.setProfil(profil);
//                    return (ProduitAssurance)this.produitAssuranceRepository.save(produitAssurance);
//                }
//            } else {
//                throw new IllegalArgumentException("Le type d'assurance ne peut pas être null ou vide.");
//            }
//        } else {
//            throw new IllegalArgumentException("Le nom de l'actif ne peut pas être null ou vide.");
//        }
//    }
//
//    public Map<String, Object> calculateSinistrePrimeRatio(Long idProduit) {
//        ProduitAssurance produitAssurance = (ProduitAssurance)this.produitAssuranceRepository.findById(idProduit).orElseThrow(() -> {
//            return new RuntimeException("Produit non trouvé pour l'ID : " + idProduit);
//        });
//        BigDecimal prime = produitAssurance.getPrime();
//        if (prime != null && prime.compareTo(BigDecimal.ZERO) != 0) {
//            BigDecimal totalSinistres = (BigDecimal)produitAssurance.getSinistres().stream().map(Sinistre::getMontantSinistre).filter(Objects::nonNull).reduce(BigDecimal.ZERO, BigDecimal::add);
//            BigDecimal ratio = totalSinistres.divide(prime, 2, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100L));
//            Map<String, Object> response = new HashMap();
//            response.put("idProduitAssurance", idProduit);
//            response.put("ratioSinistrePrime", ratio);
//            response.put("commentaire", ratio.compareTo(BigDecimal.valueOf(100L)) < 0 ? "Produit rentable" : "Produit déficitaire");
//            return response;
//        } else {
//            throw new RuntimeException("Prime non définie pour le produit ID : " + idProduit);
//        }
//    }
//
//    public BigDecimal calculateSinistrePrimeRatioForClosSinistres(String nomProduit) {
//        ProduitAssurance produitAssurance = this.produitAssuranceRepository.findByNomProduit(nomProduit);
//        if (produitAssurance == null) {
//            throw new RuntimeException("Produit Assurance introuvable pour le nom : " + nomProduit);
//        } else {
//            Set<Sinistre> sinistres = produitAssurance.getSinistres();
//            List<Sinistre> sinistresClos = sinistres.stream().filter((sinistre) -> {
//                return "clos".equalsIgnoreCase(sinistre.getEtatSinistre());
//            }).toList();
//            if (sinistresClos.isEmpty()) {
//                throw new RuntimeException("Aucun sinistre clos trouvé pour ce produit.");
//            } else {
//                BigDecimal totalMontantSinistres = (BigDecimal)sinistresClos.stream().map(Sinistre::getMontantSinistre).filter(Objects::nonNull).reduce(BigDecimal.ZERO, BigDecimal::add);
//                BigDecimal prime = produitAssurance.getPrime();
//                if (prime != null && prime.compareTo(BigDecimal.ZERO) != 0) {
//                    return totalMontantSinistres.divide(prime, 2, RoundingMode.HALF_UP);
//                } else {
//                    throw new RuntimeException("La prime est inexistante ou égale à zéro pour ce produit.");
//                }
//            }
//        }
//    }
//
//    public List<ProduitAssurance> getProduitsByDateRange(Date startDate, Date endDate) {
//        return this.produitAssuranceRepository.findByModelesActuariels_DateCalculBetween(startDate, endDate);
//    }
//}
