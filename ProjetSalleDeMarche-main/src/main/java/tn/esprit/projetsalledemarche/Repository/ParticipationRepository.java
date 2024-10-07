package tn.esprit.projetsalledemarche.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import tn.esprit.projetsalledemarche.Entity.AnalyseTechnique;
import tn.esprit.projetsalledemarche.Entity.Participation;

public interface ParticipationRepository extends JpaRepository<Participation,Long> {
}
