package tn.esprit.projetsalledemarche.Service.Servicelinda;

import tn.esprit.projetsalledemarche.Entity.Linda.Certification;

import java.util.List;
import java.util.Optional;

public interface ICertificationService {
    Certification ajouterCertification(Certification certification);
    List<Certification> afficherToutesCertifications();
    Certification modifierCertification(Long id, Certification certification);
    void supprimerCertification(Long idCertification);
}