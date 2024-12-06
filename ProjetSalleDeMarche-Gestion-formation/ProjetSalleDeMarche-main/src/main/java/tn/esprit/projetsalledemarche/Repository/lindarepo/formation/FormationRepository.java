package tn.esprit.projetsalledemarche.Repository.lindarepo.formation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.esprit.projetsalledemarche.Entity.Linda.user.Formation;
import tn.esprit.projetsalledemarche.Entity.Linda.user.Formation;

import java.util.List;
import java.util.Optional;

public interface FormationRepository extends JpaRepository<Formation,Long> {
    @Query("SELECT f FROM Formation f WHERE f.titre LIKE %:keyword% OR f.description LIKE %:keyword%")
    List<Formation> searchFormations(@Param("keyword") String keyword);

    Optional<Formation> findByType(String type);
}
