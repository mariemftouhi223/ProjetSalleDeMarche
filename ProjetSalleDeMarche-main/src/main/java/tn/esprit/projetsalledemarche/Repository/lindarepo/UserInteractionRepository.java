package tn.esprit.projetsalledemarche.Repository.lindarepo;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import tn.esprit.projetsalledemarche.Entity.Linda.UserInteraction;

import java.util.List;

@Repository
public interface UserInteractionRepository extends JpaRepository<UserInteraction,Long> {

    List<UserInteraction> findByUserId(Long userId);
}
