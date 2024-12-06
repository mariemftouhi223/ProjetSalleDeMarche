package tn.esprit.projetsalledemarche.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.projetsalledemarche.Entity.Sinistre;

import java.util.Date;
import java.util.List;


public interface SinistreRepository extends JpaRepository<Sinistre,Long> {

    List<Sinistre> findByDateSinistreBetween(Date startDate, Date endDate);
    List<Sinistre> findByDateSinistreBetweenAndEtatSinistre(Date startDate, Date endDate, String etatSinistre);


}
