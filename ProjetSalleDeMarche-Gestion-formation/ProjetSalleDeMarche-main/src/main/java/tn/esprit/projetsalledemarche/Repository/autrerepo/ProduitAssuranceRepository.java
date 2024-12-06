package tn.esprit.projetsalledemarche.Repository.autrerepo;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.projetsalledemarche.Entity.autre.ProduitAssurance;

public interface ProduitAssuranceRepository  extends JpaRepository<ProduitAssurance,Long> {
}
