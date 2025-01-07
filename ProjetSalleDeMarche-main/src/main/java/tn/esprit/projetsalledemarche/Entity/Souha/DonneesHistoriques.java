package tn.esprit.projetsalledemarche.Entity.Souha;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;
import lombok.*;
import lombok.experimental.FieldDefaults;
import tn.esprit.projetsalledemarche.Entity.enumerations.TypeGraphique;


import java.time.LocalDate;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)

public class DonneesHistoriques {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //long id;
    private Long idGraphique;

    @ElementCollection
    private List<Double> donnéesGraphique;

    @Column(nullable = false)
    private String periode;

    @Column(nullable = false)
    private String valeur;

    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private TypeGraphique typeGraphique;

    @ManyToOne
    @JsonBackReference // Prevent infinite recursion by ignoring this side during serialization
    @JoinColumn(name = "actifFinancier", nullable = false)
    private ActifFinancier actifFinancier;
    public Long getIdGraphique() {
        return idGraphique;
    }

    public void setIdGraphique(Long idGraphique) {
        this.idGraphique = idGraphique;
    }

    public List<Double> getDonnéesGraphique() {
        return donnéesGraphique;
    }

    public void setDonnéesGraphique(List<Double> donnéesGraphique) {
        this.donnéesGraphique = donnéesGraphique;
    }

    public String getPériode() {
        return periode;
    }

    public void setPériode(String periode) {
        this.periode = periode;
    }

    public TypeGraphique getTypeGraphique() {
        return typeGraphique;
    }

    public void setTypeGraphique(TypeGraphique typeGraphique) {
        this.typeGraphique = typeGraphique;
    }

    public ActifFinancier getActifFinancier() {
        return actifFinancier;
    }

    public void setActifFinancier(ActifFinancier actifFinancier) {
        this.actifFinancier = actifFinancier;
    }
}