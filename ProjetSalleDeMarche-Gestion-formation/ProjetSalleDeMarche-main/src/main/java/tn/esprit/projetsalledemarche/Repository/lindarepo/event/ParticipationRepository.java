package tn.esprit.projetsalledemarche.Repository.lindarepo.event;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.projetsalledemarche.Entity.Linda.evenment.Participation;

public interface ParticipationRepository extends JpaRepository<Participation,Long> {
}
