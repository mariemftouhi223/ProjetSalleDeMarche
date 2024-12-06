package tn.esprit.projetsalledemarche.Repository.autrerepo;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.projetsalledemarche.Entity.autre.Sinistre;

public interface SinistreRepository extends JpaRepository<Sinistre,Long> {
}
