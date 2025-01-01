package tn.esprit.projetsalledemarche.Service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.projetsalledemarche.Entity.Linda.formation.UserInteraction;
import tn.esprit.projetsalledemarche.Entity.Linda.user.Formation;
import tn.esprit.projetsalledemarche.Repository.lindarepo.formation.FormationRepository;
import tn.esprit.projetsalledemarche.Repository.lindarepo.user.UserInteractionRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserInteractionService {

    @Autowired
    private FormationRepository formationRepository;

    @Autowired
    private UserInteractionRepository userInteractionRepository;

    public UserInteraction saveInteraction(UserInteraction interaction) {
        return userInteractionRepository.save(interaction);
    }

    public List<UserInteraction> getAllInteractions() {
        return userInteractionRepository.findAll();
    }

    public int calculateInteractionScore(Formation formation) {
        return formation.getInteractions().stream()
                .mapToInt(interaction -> switch (interaction.getInteractionType()) {
                    case LOVE -> 2;
                    case VIEW -> 1;
                    case COMMENT -> 3;
                })
                .sum();
    }

    public double calculateCosineSimilarity(Map<Long, Integer> formation1, Map<Long, Integer> formation2) {
        double dotProduct = 0.0, normFormation1 = 0.0, normFormation2 = 0.0;

        for (Long userId : formation1.keySet()) {
            int score1 = formation1.getOrDefault(userId, 0);
            int score2 = formation2.getOrDefault(userId, 0);

            dotProduct += score1 * score2;
            normFormation1 += Math.pow(score1, 2);
            normFormation2 += Math.pow(score2, 2);
        }

        return dotProduct / (Math.sqrt(normFormation1) * Math.sqrt(normFormation2));
    }

    public List<Formation> getRecommendedFormations(Long userId) {
        List<UserInteraction> userInteractions = userInteractionRepository.findByUserId(userId);
        Map<Long, Integer> userFormationScores = new HashMap<>();

        // Calculate score for each formation the user has interacted with
        for (UserInteraction interaction : userInteractions) {
            Formation formation = interaction.getFormation();
            int score = calculateInteractionScore(formation);
            userFormationScores.put(formation.getId(), score);
        }

        // Retrieve all available formations
        List<Formation> allFormations = formationRepository.findAll();

        // Filter formations the user hasn't interacted with and sort by similarity score
        return allFormations.stream()
                .filter(formation -> !userFormationScores.containsKey(formation.getId()))
                .sorted(Comparator.comparingInt(this::calculateInteractionScore).reversed())
                .collect(Collectors.toList());
    }
}
