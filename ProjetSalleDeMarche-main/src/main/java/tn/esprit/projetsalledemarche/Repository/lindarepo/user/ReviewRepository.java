package tn.esprit.projetsalledemarche.Repository.lindarepo.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.projetsalledemarche.Entity.Linda.user.Review;

import java.util.List;
@Repository
public interface ReviewRepository extends JpaRepository<Review, Long>  {


    List<Review> findByFormationId(Long formationId);


}
