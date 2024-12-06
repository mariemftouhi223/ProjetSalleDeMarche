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
public class Participation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idParticipation;


    @Temporal(TemporalType.DATE)
    private Date dateInscription;


    private String statutParticipation;

    private Long idProfil;


}
