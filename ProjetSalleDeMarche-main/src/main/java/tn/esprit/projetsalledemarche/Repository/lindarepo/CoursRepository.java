package tn.esprit.projetsalledemarche.Repository.lindarepo;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.projetsalledemarche.Entity.Linda.Cours;

public interface CoursRepository  extends JpaRepository<Cours,Long> {
}
