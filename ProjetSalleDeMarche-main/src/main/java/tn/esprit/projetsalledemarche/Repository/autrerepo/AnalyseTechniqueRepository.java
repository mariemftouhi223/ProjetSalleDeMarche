package tn.esprit.projetsalledemarche.Repository.autrerepo;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.projetsalledemarche.Entity.autre.AnalyseTechnique;

public interface AnalyseTechniqueRepository extends JpaRepository<AnalyseTechnique,Long> {
}
