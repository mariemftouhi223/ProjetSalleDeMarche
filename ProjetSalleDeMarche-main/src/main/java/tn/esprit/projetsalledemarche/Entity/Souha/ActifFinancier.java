package tn.esprit.projetsalledemarche.Entity.Souha;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import tn.esprit.projetsalledemarche.Entity.enumerations.TypeActif;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ActifFinancier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idActifFinancier;

    @Column(nullable = false, length = 100)
    private String nomActif;

    @Column(nullable = false)
    private double valeurActuelle;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TypeActif typeActif;

    @Column(nullable = true)
    private String marche;

    // Relation 1-N with DonnéesHistoriques
    @OneToMany(mappedBy = "actifFinancier", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<DonneesHistoriques> historique = new ArrayList<>();

    // Relation 1-N with AnalyseTechnique
    @OneToMany(mappedBy = "actifFinancier", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<AnalyseTechnique> analysesTechniques = new ArrayList<>();

    // Relation 1-N with GraphiquePerformance
    @OneToMany(mappedBy = "actifFinancier", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<GraphiquePerformance> graphiquesPerformances = new ArrayList<>();

    // Constructors
    public ActifFinancier(String nomActif, double valeurActuelle, TypeActif typeActif, String marche) {
        this.nomActif = nomActif;
        this.valeurActuelle = valeurActuelle;
        this.typeActif = typeActif;
        this.marche = marche;
    }

    // Additional Getter
    public List<DonneesHistoriques> getHistorique() {
        return historique;
    }
}
