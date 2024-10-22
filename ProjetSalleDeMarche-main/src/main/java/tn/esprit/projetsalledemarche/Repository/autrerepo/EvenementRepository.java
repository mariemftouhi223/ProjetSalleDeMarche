package tn.esprit.projetsalledemarche.Repository.autrerepo;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.projetsalledemarche.Entity.autre.Evenement;

public interface EvenementRepository  extends JpaRepository<Evenement,Long> {
}
