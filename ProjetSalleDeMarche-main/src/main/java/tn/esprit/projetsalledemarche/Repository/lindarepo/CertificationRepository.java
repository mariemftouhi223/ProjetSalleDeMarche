package tn.esprit.projetsalledemarche.Repository.lindarepo;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.projetsalledemarche.Entity.Linda.Certification;
import tn.esprit.projetsalledemarche.Entity.autre.AnalyseTechnique;

public interface CertificationRepository extends JpaRepository<Certification,Long> {
}
