////
//// Source code recreated from a .class file by IntelliJ IDEA
//// (powered by FernFlower decompiler)
////
//
//package tn.esprit.projetsalledemarche.Entity;
//
//import jakarta.persistence.CascadeType;
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.OneToMany;
//import java.io.Serializable;
//import java.util.Set;
//import lombok.Generated;
//
//@Entity
//public class Profil implements Serializable {
//    @Id
//    @GeneratedValue(
//            strategy = GenerationType.IDENTITY
//    )
//    private long id_Profil;
//    @OneToMany(
//            mappedBy = "profil",
//            cascade = {CascadeType.ALL},
//            orphanRemoval = true
//    )
//    private Set<ProduitAssurance> produitsAssurances;
//
//    @Generated
//    public Profil() {
//    }
//
//    @Generated
//    public Profil(final long id_Profil, final Set<ProduitAssurance> produitsAssurances) {
//        this.id_Profil = id_Profil;
//        this.produitsAssurances = produitsAssurances;
//    }
//
//    @Generated
//    public long getId_Profil() {
//        return this.id_Profil;
//    }
//
//    @Generated
//    public Set<ProduitAssurance> getProduitsAssurances() {
//        return this.produitsAssurances;
//    }
//
//    @Generated
//    public void setId_Profil(final long id_Profil) {
//        this.id_Profil = id_Profil;
//    }
//
//    @Generated
//    public void setProduitsAssurances(final Set<ProduitAssurance> produitsAssurances) {
//        this.produitsAssurances = produitsAssurances;
//    }
//}
