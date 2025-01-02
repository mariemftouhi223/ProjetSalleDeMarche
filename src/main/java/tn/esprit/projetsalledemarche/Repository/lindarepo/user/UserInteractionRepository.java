package tn.esprit.projetsalledemarche.Repository.lindarepo.user;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import tn.esprit.projetsalledemarche.Entity.Linda.formation.UserInteraction;

import java.util.List;

@Repository
public interface UserInteractionRepository extends JpaRepository<UserInteraction,Long> {

    List<UserInteraction> findByUserId(Long userId);
}
