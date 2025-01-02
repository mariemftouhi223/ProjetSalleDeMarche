package tn.esprit.projetsalledemarche.Entity.Linda.formation;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import tn.esprit.projetsalledemarche.Entity.Linda.user.Formation;
import tn.esprit.projetsalledemarche.Entity.Linda.user.User;
import tn.esprit.projetsalledemarche.Entity.enumerations.ProgressionStatus;

import java.sql.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Progression {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Temporal(TemporalType.DATE)
    private Date datedebut;

    @Temporal(TemporalType.DATE)
    private Date datefin;

    private float score; // Performance de l'utilisateur dans cette progression

    @Enumerated(EnumType.STRING) // Stocker l'énumération sous forme de chaîne dans la base de données
    private ProgressionStatus status;

    @ManyToOne
    @JoinColumn(name = "formation_id")
    private Formation formation;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user; // L'utilisateur associé à cette progression

    // Nouveau champ pour suivre le nombre de modules complétés
    private int modulesCompletes;

    // Nouveau champ pour indiquer le total des modules dans la formation
    private int totalModules;

    // Durée estimée pour terminer la formation (en jours)
    private Integer dureeEstimee;

    // Date prévue de fin de la formation
    @Temporal(TemporalType.DATE)
    private Date datePrevueFin;
    @OneToMany(mappedBy = "progression")
    private List<ModulePerformance> modulePerformances; // Liste des performances liées à cette progression
}
