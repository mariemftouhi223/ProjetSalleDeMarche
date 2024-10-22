package tn.esprit.projetsalledemarche.Repository.autrerepo;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.projetsalledemarche.Entity.autre.ActifFinancier;

public interface ActifFinancierRepository  extends JpaRepository<ActifFinancier, Long> {
}
