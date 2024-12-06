package tn.esprit.projetsalledemarche.Entity.Linda.evenment;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Defi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long idDefi;

    private String nomDefi;

    private String objectif;

    private String recompense;

    private Long idParticipation;

    @Temporal(TemporalType.DATE)
    private Date dateFin;

    @ManyToOne
    @JoinColumn(name = "id_evenement", nullable = false)
    private Evenement evenement;



}

