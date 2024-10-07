package tn.esprit.projetsalledemarche.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import tn.esprit.projetsalledemarche.Entity.Portefeuille;
import tn.esprit.projetsalledemarche.Entity.ProduitAssurance;

public interface ProduitAssuranceRepository  extends JpaRepository<ProduitAssurance,Integer> {
}
