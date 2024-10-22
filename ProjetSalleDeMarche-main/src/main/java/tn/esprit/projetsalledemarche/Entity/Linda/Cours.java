package tn.esprit.projetsalledemarche.Entity.Linda;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Cours {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
     private String titre;
     private String contenu;
     private int duree;
    // Many-to-One relationship with Formation
    @ManyToOne
    @JoinColumn(name = "formation_id")
    private Formation formation;
    @OneToMany(mappedBy = "cours", cascade = CascadeType.ALL)
    private List<Quiz> quizList;
}
