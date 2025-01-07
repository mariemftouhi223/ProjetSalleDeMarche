package tn.esprit.projetsalledemarche.Entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Sinistre implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSinistre;

    private Date dateSinistre;

    private BigDecimal montantSinistre;

    private String etatSinistre; // ex: "ouvert", "en cours", "clos"

    // Relation avec ProduitAssurance
    @ManyToOne
    @JoinColumn(name = "idProduitAssurance", nullable = false)
    @JsonIgnore
    private ProduitAssurance produitAssurance;
}
