package tn.esprit.projetsalledemarche.Entity.Souha;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import tn.esprit.projetsalledemarche.Entity.enumerations.TypeIndicateur;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AnalyseTechnique {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //long id;
    private Long idAnalyse;

    @Column(nullable = false)
    private double valeurIndicateur;

    @Column(nullable = false)
    private LocalDate dateAnalyse;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TypeIndicateur typeIndicateur;

    @ManyToOne
    @JoinColumn(name = "idActifFinancier", nullable = false)
    private ActifFinancier actifFinancier;

    // Getters et setters
    public Long getIdAnalyse() {
        return idAnalyse;
    }

    public void setIdAnalyse(Long idAnalyse) {
        this.idAnalyse = idAnalyse;
    }

    public double getValeurIndicateur() {
        return valeurIndicateur;
    }

    public void setValeurIndicateur(double valeurIndicateur) {
        this.valeurIndicateur = valeurIndicateur;
    }

    public LocalDate getDateAnalyse() {
        return dateAnalyse;
    }

    public void setDateAnalyse(LocalDate dateAnalyse) {
        this.dateAnalyse = dateAnalyse;
    }

    public TypeIndicateur getTypeIndicateur() {
        return typeIndicateur;
    }

    public void setTypeIndicateur(TypeIndicateur typeIndicateur) {
        this.typeIndicateur = typeIndicateur;
    }

    public ActifFinancier getActifFinancier() {
        return actifFinancier;
    }

    public void setActifFinancier(ActifFinancier actifFinancier) {
        this.actifFinancier = actifFinancier;
    }
}
