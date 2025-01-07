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
//import tn.esprit.projetsalledemarche.Entity.Sinistre;
//
//public interface SinistreRepository extends JpaRepository<Sinistre, Long> {
//    List<Sinistre> findByDateSinistreBetween(Date startDate, Date endDate);
//
//    List<Sinistre> findByDateSinistreBetweenAndEtatSinistre(Date startDate, Date endDate, String etatSinistre);
//}
