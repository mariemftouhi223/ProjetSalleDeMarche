package tn.esprit.projetsalledemarche.Repository.lindarepo.event;


import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.projetsalledemarche.Entity.Linda.evenment.Interaction;

import java.util.List;
import java.util.Optional;

public interface InteractionRepository extends JpaRepository<Interaction, Long> {
    List<Interaction> findByUserId(Long userId);
    Optional<Interaction> findFirstByUserIdAndEventIdAndType(Long userId, Long eventId, String type);
}
