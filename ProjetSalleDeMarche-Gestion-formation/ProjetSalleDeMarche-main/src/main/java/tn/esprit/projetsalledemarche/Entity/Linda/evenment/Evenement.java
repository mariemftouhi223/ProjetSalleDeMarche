package tn.esprit.projetsalledemarche.Entity.Linda.evenment;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Evenement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEvenement;

    private String nomEvenement;
    @Temporal(TemporalType.DATE)
    private Date dateDebut;
    @Temporal(TemporalType.DATE)
    private Date dateFin;
    private String description;

    private Long idParticipation;

    private String typeEvenement; // Exemple : Séminaire, Atelier, Formation
    private int nombreParticipantsPrevus; // Nombre estimé de participants
    private String impact; // Faible, Moyen, Élevé
    private boolean succesPrecedent; // Indique si l'événement précédent du même type était un succès

    private int organiser;
    @ElementCollection
    private List<String> emailsParticipants;

    private double prix; // Prix de l'événement
    private boolean isPayant; // Indique si l'événement est payant


}