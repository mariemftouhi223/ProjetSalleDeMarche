////
//// Source code recreated from a .class file by IntelliJ IDEA
//// (powered by FernFlower decompiler)
////
//
//package tn.esprit.projetsalledemarche.Repository;
//
//import java.util.Date;
//import java.util.List;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//import tn.esprit.projetsalledemarche.Entity.ProduitAssurance;
//
//public interface ProduitAssuranceRepository extends JpaRepository<ProduitAssurance, Long> {
//    @Query("SELECT p FROM ProduitAssurance p WHERE p.NomProduit = :nomProduit")
//    ProduitAssurance findByNomProduit(@Param("nomProduit") String nomProduit);
//
//    List<ProduitAssurance> findByModelesActuariels_DateCalculBetween(Date startDate, Date endDate);
//}
