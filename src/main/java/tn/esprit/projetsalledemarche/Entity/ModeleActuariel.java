package tn.esprit.projetsalledemarche.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ModeleActuariel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idModele;

    private String typeModele; // ex: "modélisation des sinistres", "provisionnement"

    private Date dateCalcul;

    private BigDecimal valeurEstimee;

    @Enumerated(EnumType.STRING)
    private TypeModele Mtype;

    private String nomActif;  // Nom de l'actif, ex: "BTC-USD" ou "AAPL"

    // Relation avec ProduitAssurance
    @ManyToOne
    @JoinColumn(name = "idProduitAssurance", nullable = false)
    @JsonBackReference
    private ProduitAssurance produitAssurance;
}

