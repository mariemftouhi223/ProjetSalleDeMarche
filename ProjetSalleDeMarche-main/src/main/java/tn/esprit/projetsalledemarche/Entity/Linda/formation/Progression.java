package tn.esprit.projetsalledemarche.Entity.Linda.formation;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import tn.esprit.projetsalledemarche.Entity.Linda.user.Formation;
import tn.esprit.projetsalledemarche.Entity.enumerations.ProgressionStatus;

import java.sql.Date;

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

    private float score;

    @Enumerated(EnumType.STRING) // Stocker l'énumération sous forme de chaîne dans la base de données
    private ProgressionStatus status;

    @ManyToOne
    @JoinColumn(name = "formation_id")
    private Formation formation;



}
