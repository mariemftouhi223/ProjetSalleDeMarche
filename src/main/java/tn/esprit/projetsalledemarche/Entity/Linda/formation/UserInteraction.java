package tn.esprit.projetsalledemarche.Entity.Linda.formation;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import tn.esprit.projetsalledemarche.Entity.Linda.user.Formation;
import tn.esprit.projetsalledemarche.Entity.enumerations.InteractionType;

import java.time.LocalDateTime;
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserInteraction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;  // ID de l'utilisateur
   // ID de la formation

    @Enumerated(EnumType.STRING)
    @Column(name = "interaction_type", nullable = false)
    private InteractionType interactionType;

    private LocalDateTime timestamp = LocalDateTime.now();  // Da
    @ManyToOne
    @JoinColumn(name = "formation_id", nullable = false)  // Gère la colonne formation_id
    private Formation formation;
}