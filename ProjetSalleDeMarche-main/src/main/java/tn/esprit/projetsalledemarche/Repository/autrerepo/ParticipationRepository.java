package tn.esprit.projetsalledemarche.Repository.autrerepo;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.projetsalledemarche.Entity.autre.Participation;

public interface ParticipationRepository extends JpaRepository<Participation,Long> {
}
