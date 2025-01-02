package tn.esprit.projetsalledemarche.Entity.Linda.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tn.esprit.projetsalledemarche.Entity.Linda.user.Formation;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;  // Titre de l'avis, par exemple "Excellent événement" ou "Formation très utile"

    private String content;  // Contenu de l'avis, détaillant les impressions de l'utilisateur

    private int stars;  // Note sur 5 étoiles, représentant l'évaluation globale de l'événement/formation

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "formation_id")
    private Formation formation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String sentiment; // Champ pour stocker le sentiment associé à l'avis

    // Getter pour sentiment
    public String getSentiment() {
        return sentiment;
    }

    // Setter pour sentiment
    public void setSentiment(String sentiment) {
        this.sentiment = sentiment;
    }
}
