package tn.esprit.projetsalledemarche.Service.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.projetsalledemarche.Entity.Linda.evenment.Interaction;
import tn.esprit.projetsalledemarche.Repository.lindarepo.event.InteractionRepository;

import java.util.List;

@Service
public class InteractionService {

    @Autowired
    private InteractionRepository interactionRepository;

    public Interaction likeEvent(Long userId, Long eventId) {
        Interaction interaction = new Interaction();
        interaction.setUserId(userId);
        interaction.setEventId(eventId);
        interaction.setLiked(true); // Renommé en isLiked
        return interactionRepository.save(interaction);
    }

    public Interaction saveComment(Long userId, Long eventId, String commentaire) {
        Interaction interaction = new Interaction();
        interaction.setUserId(userId);
        interaction.setEventId(eventId);
        interaction.setType("commentaire");
        interaction.setCommentaire(commentaire);
        return interactionRepository.save(interaction);
    }

    public Interaction saveEvent(Long userId, Long eventId) {
        Interaction interaction = new Interaction();
        interaction.setUserId(userId);
        interaction.setEventId(eventId);
        interaction.setSauvegarder(true);
        return interactionRepository.save(interaction);
    }

    public List<Interaction> findByUserId(Long userId) {
        return interactionRepository.findByUserId(userId);
    }
}
