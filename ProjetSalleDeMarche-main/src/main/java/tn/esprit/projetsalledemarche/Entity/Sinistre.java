////
//// Source code recreated from a .class file by IntelliJ IDEA
//// (powered by FernFlower decompiler)
////
//
//package tn.esprit.projetsalledemarche.Entity;
//
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.JoinColumn;
//import jakarta.persistence.ManyToOne;
//import java.io.Serializable;
//import java.math.BigDecimal;
//import java.util.Date;
//import lombok.Generated;
//
//@Entity
//public class Sinistre implements Serializable {
//    @Id
//    @GeneratedValue(
//            strategy = GenerationType.IDENTITY
//    )
//    private Long idSinistre;
//    private Date dateSinistre;
//    private BigDecimal montantSinistre;
//    private String etatSinistre;
//    @ManyToOne
//    @JoinColumn(
//            name = "idProduitAssurance",
//            nullable = false
//    )
//    private ProduitAssurance produitAssurance;
//
//    @Generated
//    public Sinistre() {
//    }
//
//    @Generated
//    public Sinistre(final Long idSinistre, final Date dateSinistre, final BigDecimal montantSinistre, final String etatSinistre, final ProduitAssurance produitAssurance) {
//        this.idSinistre = idSinistre;
//        this.dateSinistre = dateSinistre;
//        this.montantSinistre = montantSinistre;
//        this.etatSinistre = etatSinistre;
//        this.produitAssurance = produitAssurance;
//    }
//
//    @Generated
//    public Long getIdSinistre() {
//        return this.idSinistre;
//    }
//
//    @Generated
//    public Date getDateSinistre() {
//        return this.dateSinistre;
//    }
//
//    @Generated
//    public BigDecimal getMontantSinistre() {
//        return this.montantSinistre;
//    }
//
//    @Generated
//    public String getEtatSinistre() {
//        return this.etatSinistre;
//    }
//
//    @Generated
//    public ProduitAssurance getProduitAssurance() {
//        return this.produitAssurance;
//    }
//
//    @Generated
//    public void setIdSinistre(final Long idSinistre) {
//        this.idSinistre = idSinistre;
//    }
//
//    @Generated
//    public void setDateSinistre(final Date dateSinistre) {
//        this.dateSinistre = dateSinistre;
//    }
//
//    @Generated
//    public void setMontantSinistre(final BigDecimal montantSinistre) {
//        this.montantSinistre = montantSinistre;
//    }
//
//    @Generated
//    public void setEtatSinistre(final String etatSinistre) {
//        this.etatSinistre = etatSinistre;
//    }
//
//    @Generated
//    public void setProduitAssurance(final ProduitAssurance produitAssurance) {
//        this.produitAssurance = produitAssurance;
//    }
//}
