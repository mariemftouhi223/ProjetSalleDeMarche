package tn.esprit.projetsalledemarche.Repository.lindarepo;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.projetsalledemarche.Entity.Linda.Progression;

public interface ProgressionRepository extends JpaRepository<Progression,Long> {
}
