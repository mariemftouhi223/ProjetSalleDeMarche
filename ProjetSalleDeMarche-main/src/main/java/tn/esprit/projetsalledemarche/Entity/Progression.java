package tn.esprit.projetsalledemarche.Entity;

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
public class Progression {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private Date datedebut;
    private Date datefin;
    private float score ;
    @ManyToOne
    @JoinColumn(name = "formation_id")
    private Formation formation;
}
