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
public class Certification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nom;
    private String description;
    @Temporal(TemporalType.DATE)
    private Date dateobtention;
    @ManyToOne
    @JoinColumn(name = "formation_id")
    private Formation formation;
}