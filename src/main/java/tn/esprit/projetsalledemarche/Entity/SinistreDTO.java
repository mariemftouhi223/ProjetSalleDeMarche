package tn.esprit.projetsalledemarche.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SinistreDTO {
    private Long idSinistre;
    private Date dateSinistre;
    private BigDecimal montantSinistre;
    private String etatSinistre;
    private String nomProduit; // NomProduit from ProduitAssurance
}

