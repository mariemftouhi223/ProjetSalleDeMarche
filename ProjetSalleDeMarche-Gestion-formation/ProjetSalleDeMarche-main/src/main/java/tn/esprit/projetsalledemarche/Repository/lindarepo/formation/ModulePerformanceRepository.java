package tn.esprit.projetsalledemarche.Repository.lindarepo.formation;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.projetsalledemarche.Entity.Linda.formation.Certification;
import tn.esprit.projetsalledemarche.Entity.Linda.formation.ModulePerformance;

import java.util.List;

public interface ModulePerformanceRepository extends JpaRepository<ModulePerformance,Long> {
}
