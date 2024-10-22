package tn.esprit.projetsalledemarche.Entity.Linda;

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
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String titre;
    @Temporal(TemporalType.DATE)
    private Date datecreation;
    @ManyToOne
    @JoinColumn(name = "cours_id")
    private Cours cours;
}
