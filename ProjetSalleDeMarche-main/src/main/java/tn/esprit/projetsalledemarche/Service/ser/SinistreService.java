////
//// Source code recreated from a .class file by IntelliJ IDEA
//// (powered by FernFlower decompiler)
////
//
//package tn.esprit.projetsalledemarche.Service.ser;
//
//import java.math.BigDecimal;
//import java.util.Date;
//import java.util.List;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import tn.esprit.projetsalledemarche.Entity.ProduitAssurance;
//import tn.esprit.projetsalledemarche.Entity.Sinistre;
//import tn.esprit.projetsalledemarche.Repository.ProduitAssuranceRepository;
//import tn.esprit.projetsalledemarche.Repository.SinistreRepository;
//import tn.esprit.projetsalledemarche.Service.IMP.ISinistreService;
//
//@Service
//public class SinistreService implements ISinistreService {
//    @Autowired
//    private final SinistreRepository sinistreRepository;
//    @Autowired
//    private ProduitAssuranceRepository produitAssuranceRepository;
//
//    @Autowired
//    public SinistreService(SinistreRepository sinistreRepository) {
//        this.sinistreRepository = sinistreRepository;
//    }
//
//    public Sinistre addSinistre(Sinistre sinistre) {
//        return (Sinistre)this.sinistreRepository.save(sinistre);
//    }
//
//    public Sinistre getSinistre(long idSinistre) {
//        return (Sinistre)this.sinistreRepository.findById(idSinistre).orElse((Object)null);
//    }
//
//    public List<Sinistre> getAllSinistres() {
//        return this.sinistreRepository.findAll();
//    }
//
//    public void deleteSinistre(Long idSinistre) {
//        this.sinistreRepository.deleteById(idSinistre);
//    }
//
//    public Sinistre updateSinistre(Sinistre sinistre) {
//        return (Sinistre)this.sinistreRepository.save(sinistre);
//    }
//
//    public Sinistre createSinistre(String nomProduit, Date dateSinistre, BigDecimal montantSinistre, String etatSinistre) {
//        ProduitAssurance produitAssurance = this.produitAssuranceRepository.findByNomProduit(nomProduit);
//        if (produitAssurance == null) {
//            throw new RuntimeException("Produit Assurance introuvable pour le nom : " + nomProduit);
//        } else if (montantSinistre.compareTo(produitAssurance.getCouverture()) > 0) {
//            throw new RuntimeException("Montant du sinistre dépasse la couverture du produit.");
//        } else {
//            Sinistre sinistre = new Sinistre();
//            sinistre.setDateSinistre(dateSinistre);
//            sinistre.setMontantSinistre(montantSinistre);
//            sinistre.setEtatSinistre(etatSinistre);
//            sinistre.setProduitAssurance(produitAssurance);
//            return (Sinistre)this.sinistreRepository.save(sinistre);
//        }
//    }
//
//    public List<Sinistre> getSinistresByDateRange(Date startDate, Date endDate) {
//        return this.sinistreRepository.findByDateSinistreBetween(startDate, endDate);
//    }
//
//    public List<Sinistre> getClosedSinistresByDateRange(Date startDate, Date endDate) {
//        return this.sinistreRepository.findByDateSinistreBetweenAndEtatSinistre(startDate, endDate, "clos");
//    }
//}
