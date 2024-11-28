package tn.esprit.projetsalledemarche.Service.Servicelinda.IMP;

import tn.esprit.projetsalledemarche.Entity.Linda.formation.Certification;

import java.util.List;

public interface ICertificationService {
    Certification ajouterCertification(Certification certification);
    List<Certification> afficherToutesCertifications();
    Certification modifierCertification(Long id, Certification certification);
    void supprimerCertification(Long idCertification);
}