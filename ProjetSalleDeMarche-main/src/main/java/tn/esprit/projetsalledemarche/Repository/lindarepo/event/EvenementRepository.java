package tn.esprit.projetsalledemarche.Repository.lindarepo.event;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.projetsalledemarche.Entity.Linda.evenment.Evenement;

public interface EvenementRepository  extends JpaRepository<Evenement,Long> {
}
