package tn.esprit.projetsalledemarche.Entity;



import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProduitAssurance implements  Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idProduit ;

    private String NomProduit;

    private BigDecimal prime;
    private BigDecimal couverture;

    @Enumerated(EnumType.STRING)
    private TypeAssurance Atype;

    @JsonIgnore

    @ManyToOne
    @JoinColumn(name = "idProfil", nullable = false)
    private Profil profil;


    @OneToMany(mappedBy = "produitAssurance", cascade = CascadeType.ALL)
    private Set<Sinistre> sinistres;

    @OneToMany(mappedBy = "produitAssurance", cascade = CascadeType.ALL)
    private Set<ModeleActuariel> modelesActuariels;



}
