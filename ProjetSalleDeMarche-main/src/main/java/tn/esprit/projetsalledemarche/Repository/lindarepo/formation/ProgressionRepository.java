package tn.esprit.projetsalledemarche.Repository.lindarepo.formation;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.projetsalledemarche.Entity.Linda.formation.Progression;

public interface ProgressionRepository extends JpaRepository<Progression,Long> {
}
