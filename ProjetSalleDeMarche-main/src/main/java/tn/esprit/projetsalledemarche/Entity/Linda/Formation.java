package tn.esprit.projetsalledemarche.Entity.Linda;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Formation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String titre;

    private String description;

    @Temporal(TemporalType.DATE)
    private Date dateCreation;

    // Relation One-to-Many avec Progression
    @OneToMany(mappedBy = "formation", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Progression> progressions;

    // Relation One-to-Many avec Cours
    @OneToMany(mappedBy = "formation", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Cours> cours;

    // Relation One-to-Many avec Certification
    @OneToMany(mappedBy = "formation", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Certification> certifications;

    // Relation One-to-Many avec UserInteraction
    @OneToMany(mappedBy = "formation", cascade = CascadeType.ALL)
    @JsonIgnore  // Ajout de @JsonIgnore pour éviter la boucle infinie
    private List<UserInteraction> interactions;
}
