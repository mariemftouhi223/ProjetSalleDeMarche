package tn.esprit.projetsalledemarche.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import tn.esprit.projetsalledemarche.Entity.ActifFinancier;

public interface ActifFinancierRepository  extends JpaRepository<ActifFinancier, Long> {
}
