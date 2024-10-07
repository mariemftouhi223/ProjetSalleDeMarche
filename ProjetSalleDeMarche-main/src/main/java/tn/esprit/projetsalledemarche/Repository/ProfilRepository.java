package tn.esprit.projetsalledemarche.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import tn.esprit.projetsalledemarche.Entity.AnalyseTechnique;
import tn.esprit.projetsalledemarche.Entity.Portefeuille;
import tn.esprit.projetsalledemarche.Entity.Profil;

public interface ProfilRepository extends JpaRepository<Profil,Long> {
}
