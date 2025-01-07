////
//// Source code recreated from a .class file by IntelliJ IDEA
//// (powered by FernFlower decompiler)
////
//
//package tn.esprit.projetsalledemarche.Entity;
//
//import jakarta.persistence.Entity;
//import jakarta.persistence.EnumType;
//import jakarta.persistence.Enumerated;
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
//public class ModeleActuariel implements Serializable {
//    @Id
//    @GeneratedValue(
//            strategy = GenerationType.IDENTITY
//    )
//    private Long idModele;
//    private String typeModele;
//    private Date dateCalcul;
//    private BigDecimal valeurEstimee;
//    @Enumerated(EnumType.STRING)
//    private TypeModele Mtype;
//    private String nomActif;
//    @ManyToOne
//    @JoinColumn(
//            name = "idProduitAssurance",
//            nullable = false
//    )
//    private ProduitAssurance produitAssurance;
//
//    @Generated
//    public ModeleActuariel() {
//    }
//
//    @Generated
//    public ModeleActuariel(final Long idModele, final String typeModele, final Date dateCalcul, final BigDecimal valeurEstimee, final TypeModele Mtype, final String nomActif, final ProduitAssurance produitAssurance) {
//        this.idModele = idModele;
//        this.typeModele = typeModele;
//        this.dateCalcul = dateCalcul;
//        this.valeurEstimee = valeurEstimee;
//        this.Mtype = Mtype;
//        this.nomActif = nomActif;
//        this.produitAssurance = produitAssurance;
//    }
//
//    @Generated
//    public Long getIdModele() {
//        return this.idModele;
//    }
//
//    @Generated
//    public String getTypeModele() {
//        return this.typeModele;
//    }
//
//    @Generated
//    public Date getDateCalcul() {
//        return this.dateCalcul;
//    }
//
//    @Generated
//    public BigDecimal getValeurEstimee() {
//        return this.valeurEstimee;
//    }
//
//    @Generated
//    public TypeModele getMtype() {
//        return this.Mtype;
//    }
//
//    @Generated
//    public String getNomActif() {
//        return this.nomActif;
//    }
//
//    @Generated
//    public ProduitAssurance getProduitAssurance() {
//        return this.produitAssurance;
//    }
//
//    @Generated
//    public void setIdModele(final Long idModele) {
//        this.idModele = idModele;
//    }
//
//    @Generated
//    public void setTypeModele(final String typeModele) {
//        this.typeModele = typeModele;
//    }
//
//    @Generated
//    public void setDateCalcul(final Date dateCalcul) {
//        this.dateCalcul = dateCalcul;
//    }
//
//    @Generated
//    public void setValeurEstimee(final BigDecimal valeurEstimee) {
//        this.valeurEstimee = valeurEstimee;
//    }
//
//    @Generated
//    public void setMtype(final TypeModele Mtype) {
//        this.Mtype = Mtype;
//    }
//
//    @Generated
//    public void setNomActif(final String nomActif) {
//        this.nomActif = nomActif;
//    }
//
//    @Generated
//    public void setProduitAssurance(final ProduitAssurance produitAssurance) {
//        this.produitAssurance = produitAssurance;
//    }
//}
