package tn.esprit.projetsalledemarche.Entity.Linda.user;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class RecommandationResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String formation;
    private String evenement;
    private String symbol; // Nouveau champ symbol
    private Double performance; // Nouveau champ performance

    // Les getters et setters sont automatiquement générés par Lombok grâce à l'annotation @Data
}
