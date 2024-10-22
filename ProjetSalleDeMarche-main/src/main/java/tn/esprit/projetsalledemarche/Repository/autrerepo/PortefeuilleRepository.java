package tn.esprit.projetsalledemarche.Repository.autrerepo;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.projetsalledemarche.Entity.autre.Portefeuille;

public interface PortefeuilleRepository  extends JpaRepository<Portefeuille,Long> {
}
