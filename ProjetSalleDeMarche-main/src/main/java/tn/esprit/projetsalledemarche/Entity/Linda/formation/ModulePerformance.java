package tn.esprit.projetsalledemarche.Entity.Linda.formation;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import tn.esprit.projetsalledemarche.Entity.Linda.user.User;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ModulePerformance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user; // L'utilisateur qui a réalisé la formation

    @ManyToOne
    @JoinColumn(name = "module_id")
    private FormationModule module; // Le module spécifique sur lequel l'utilisateur a travaillé


    private float score; // Le score de l'utilisateur dans ce module

    private int durationSpent; // Temps passé sur le module

    private boolean isCompleted; // Indicateur de si l'utilisateur a complété ce module ou non

    @ManyToOne
    @JoinColumn(name = "progression_id", referencedColumnName = "id")
    private Progression progression; // Progression de l'utilisateur dans ce module

}
