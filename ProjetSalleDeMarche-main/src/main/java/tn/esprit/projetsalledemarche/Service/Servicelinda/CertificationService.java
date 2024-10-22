package tn.esprit.projetsalledemarche.Service.Servicelinda;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.projetsalledemarche.Entity.Linda.Certification;
import tn.esprit.projetsalledemarche.Repository.lindarepo.CertificationRepository;
import tn.esprit.projetsalledemarche.Service.Servicelinda.ICertificationService;

import java.util.List;
import java.util.Optional;

@Service
public class CertificationService implements ICertificationService {
    @Autowired
    private CertificationRepository certificationRepository;

    /**
     * Ajoute une nouvelle certification à la base de données.
     * @param certification La certification à ajouter.
     * @return La certification ajoutée.
     */
    @Override
    public Certification ajouterCertification(Certification certification) {
        return certificationRepository.save(certification);
    }

    /**
     * Récupère toutes les certifications de la base de données.
     * @return Une liste de certifications.
     */
    @Override
    public List<Certification> afficherToutesCertifications() {
        return certificationRepository.findAll();
    }

    /**
     * Modifie une certification existante dans la base de données.
     * @param id L'identifiant de la certification à modifier.
     * @param certification Les nouvelles informations de la certification.
     * @return La certification modifiée ou null si la certification n'existe pas.
     */
    public Certification modifierCertification(Long id, Certification certification) {
        Certification existingCertification = certificationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Certification not found with id " + id));

        existingCertification.setNom(certification.getNom());
        existingCertification.setDescription(certification.getDescription());
        existingCertification.setDateobtention(certification.getDateobtention());

        return certificationRepository.save(existingCertification);
    }


    /**
     * Supprime une certification par ID.
     * @param idCertification L'identifiant de la certification à supprimer.
     */
    @Override
    public void supprimerCertification(Long idCertification) {
        certificationRepository.deleteById(idCertification);
    }
}
