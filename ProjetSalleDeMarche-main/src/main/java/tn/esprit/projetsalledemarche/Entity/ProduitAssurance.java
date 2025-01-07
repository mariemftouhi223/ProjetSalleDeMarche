////
//// Source code recreated from a .class file by IntelliJ IDEA
//// (powered by FernFlower decompiler)
////
//
//package tn.esprit.projetsalledemarche.Entity;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import jakarta.persistence.CascadeType;
//import jakarta.persistence.Entity;
//import jakarta.persistence.EnumType;
//import jakarta.persistence.Enumerated;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.JoinColumn;
//import jakarta.persistence.ManyToOne;
//import jakarta.persistence.OneToMany;
//import java.io.Serializable;
//import java.math.BigDecimal;
//import java.util.Set;
//import lombok.Generated;
//
//@Entity
//public class ProduitAssurance implements Serializable {
//    @Id
//    @GeneratedValue(
//            strategy = GenerationType.IDENTITY
//    )
//    private long idProduit;
//    private String NomProduit;
//    private BigDecimal prime;
//    private BigDecimal couverture;
//    @Enumerated(EnumType.STRING)
//    private TypeAssurance Atype;
//    @JsonIgnore
//    @ManyToOne
//    @JoinColumn(
//            name = "idProfil",
//            nullable = false
//    )
//    private Profil profil;
//    @OneToMany(
//            mappedBy = "produitAssurance",
//            cascade = {CascadeType.ALL}
//    )
//    private Set<Sinistre> sinistres;
//    @OneToMany(
//            mappedBy = "produitAssurance",
//            cascade = {CascadeType.ALL}
//    )
//    private Set<ModeleActuariel> modelesActuariels;
//
//    @Generated
//    public ProduitAssurance() {
//    }
//
//    @Generated
//    public ProduitAssurance(final long idProduit, final String NomProduit, final BigDecimal prime, final BigDecimal couverture, final TypeAssurance Atype, final Profil profil, final Set<Sinistre> sinistres, final Set<ModeleActuariel> modelesActuariels) {
//        this.idProduit = idProduit;
//        this.NomProduit = NomProduit;
//        this.prime = prime;
//        this.couverture = couverture;
//        this.Atype = Atype;
//        this.profil = profil;
//        this.sinistres = sinistres;
//        this.modelesActuariels = modelesActuariels;
//    }
//
//    @Generated
//    public long getIdProduit() {
//        return this.idProduit;
//    }
//
//    @Generated
//    public String getNomProduit() {
//        return this.NomProduit;
//    }
//
//    @Generated
//    public BigDecimal getPrime() {
//        return this.prime;
//    }
//
//    @Generated
//    public BigDecimal getCouverture() {
//        return this.couverture;
//    }
//
//    @Generated
//    public TypeAssurance getAtype() {
//        return this.Atype;
//    }
//
//    @Generated
//    public Profil getProfil() {
//        return this.profil;
//    }
//
//    @Generated
//    public Set<Sinistre> getSinistres() {
//        return this.sinistres;
//    }
//
//    @Generated
//    public Set<ModeleActuariel> getModelesActuariels() {
//        return this.modelesActuariels;
//    }
//
//    @Generated
//    public void setIdProduit(final long idProduit) {
//        this.idProduit = idProduit;
//    }
//
//    @Generated
//    public void setNomProduit(final String NomProduit) {
//        this.NomProduit = NomProduit;
//    }
//
//    @Generated
//    public void setPrime(final BigDecimal prime) {
//        this.prime = prime;
//    }
//
//    @Generated
//    public void setCouverture(final BigDecimal couverture) {
//        this.couverture = couverture;
//    }
//
//    @Generated
//    public void setAtype(final TypeAssurance Atype) {
//        this.Atype = Atype;
//    }
//
//    @JsonIgnore
//    @Generated
//    public void setProfil(final Profil profil) {
//        this.profil = profil;
//    }
//
//    @Generated
//    public void setSinistres(final Set<Sinistre> sinistres) {
//        this.sinistres = sinistres;
//    }
//
//    @Generated
//    public void setModelesActuariels(final Set<ModeleActuariel> modelesActuariels) {
//        this.modelesActuariels = modelesActuariels;
//    }
//}
