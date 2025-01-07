package tn.esprit.projetsalledemarche.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.esprit.projetsalledemarche.Entity.Sinistre;
import tn.esprit.projetsalledemarche.Entity.SinistreDTO;

import java.util.Date;
import java.util.List;


public interface SinistreRepository extends JpaRepository<Sinistre,Long> {

    List<Sinistre> findByDateSinistreBetween(Date startDate, Date endDate);
    List<Sinistre> findByDateSinistreBetweenAndEtatSinistre(Date startDate, Date endDate, String etatSinistre);
    @Query("SELECT new tn.esprit.projetsalledemarche.Entity.SinistreDTO(s.idSinistre, s.dateSinistre, s.montantSinistre, s.etatSinistre, p.NomProduit) " +
            "FROM Sinistre s JOIN s.produitAssurance p WHERE s.idSinistre = :idSinistre")
    SinistreDTO findSinistreByIdWithProduitNom(@Param("idSinistre") Long idSinistre);


}
