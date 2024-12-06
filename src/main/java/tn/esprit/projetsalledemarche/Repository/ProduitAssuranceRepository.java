package tn.esprit.projetsalledemarche.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.esprit.projetsalledemarche.Entity.ProduitAssurance;

import java.util.Date;
import java.util.List;

public interface ProduitAssuranceRepository extends JpaRepository < ProduitAssurance, Long> {


    @Query("SELECT p FROM ProduitAssurance p WHERE p.NomProduit = :nomProduit")
    ProduitAssurance findByNomProduit(@Param("nomProduit") String nomProduit);



    List<ProduitAssurance> findByModelesActuariels_DateCalculBetween(Date startDate, Date endDate);



}
