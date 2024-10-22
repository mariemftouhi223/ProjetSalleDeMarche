package tn.esprit.projetsalledemarche.Repository.autrerepo;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.projetsalledemarche.Entity.autre.Profil;

public interface ProfilRepository extends JpaRepository<Profil,Long> {
}
