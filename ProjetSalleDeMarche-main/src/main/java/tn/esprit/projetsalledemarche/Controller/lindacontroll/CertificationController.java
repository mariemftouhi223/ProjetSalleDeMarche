package tn.esprit.projetsalledemarche.Controller.lindacontroll;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.projetsalledemarche.Entity.Linda.Certification;
import tn.esprit.projetsalledemarche.Service.Servicelinda.CertificationService;

import java.util.List;

@RestController
@RequestMapping("/certifications")
public class CertificationController {

    @Autowired
    private CertificationService certificationService;

    // Ajouter une certification
    @PostMapping("/ajouter")
    public ResponseEntity<Certification> ajouterCertification(@RequestBody Certification certification) {
        Certification createdCertification = certificationService.ajouterCertification(certification);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCertification); // 201 Created
    }

    // Afficher toutes les certifications
    @GetMapping("/afficher")
    public ResponseEntity<List<Certification>> afficherToutesCertifications() {
        List<Certification> certifications = certificationService.afficherToutesCertifications();
        return ResponseEntity.ok(certifications); // 200 OK
    }

    // Modifier une certification
    @PutMapping("/modifier/{id}")
    public ResponseEntity<Certification> modifier(@PathVariable Long id, @RequestBody Certification certification) {
        try {
            Certification updatedCertification = certificationService.modifierCertification(id, certification);
            return ResponseEntity.ok(updatedCertification); // 200 OK
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build(); // 404 Not Found
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500 Internal Server Error
        }
    }


    // Supprimer une certification par ID
    @DeleteMapping("/supprimer/{id}")
    public ResponseEntity<Void> supprimerCertification(@PathVariable Long id) {
        try {
            certificationService.supprimerCertification(id);
            return ResponseEntity.noContent().build(); // 204 No Content
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build(); // 404 Not Found
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500 Internal Server Error
        }
    }
}
