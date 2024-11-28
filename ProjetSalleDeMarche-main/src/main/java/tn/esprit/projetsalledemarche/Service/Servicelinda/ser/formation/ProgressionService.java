package tn.esprit.projetsalledemarche.Service.Servicelinda.ser.formation;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.projetsalledemarche.Entity.Linda.formation.Progression;
import tn.esprit.projetsalledemarche.Entity.enumerations.ProgressionStatus;
import tn.esprit.projetsalledemarche.Repository.lindarepo.formation.ProgressionRepository;
import tn.esprit.projetsalledemarche.Service.Servicelinda.IMP.IProgressionService;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service


public class ProgressionService implements IProgressionService {

    @Autowired
    private ProgressionRepository progressionRepository;

    // Calcule la durée totale de formation (en jours) pour une formation spécifique
    public long calculateTrainingDuration(Long progressionId) {
        Progression progression = progressionRepository.findById(progressionId)
                .orElseThrow(() -> new EntityNotFoundException("Progression non trouvée"));

        if (progression.getDatedebut() != null && progression.getDatefin() != null) {
            // Convertit java.sql.Date en LocalDate sans utiliser toInstant()
            LocalDate startDate = convertSqlDateToLocalDate(progression.getDatedebut());
            LocalDate endDate = convertSqlDateToLocalDate(progression.getDatefin());

            // Calcul de la durée en jours
            return ChronoUnit.DAYS.between(startDate, endDate);
        }
        return 0; // Retourne 0 si l'une des dates est nulle, ce qui signifie que la formation n'est pas terminée
    }
    // Méthode pour vérifier si une progression est terminée
    public boolean isProgressionCompleted(Long progressionId) {
        Progression progression = progressionRepository.findById(progressionId)
                .orElseThrow(() -> new EntityNotFoundException("Progression non trouvée"));

        if (progression.getDatefin() != null) {
            LocalDate endDate = progression.getDatefin().toLocalDate();
            return !endDate.isAfter(LocalDate.now());
        }
        return false;
    }

    // Méthode utilitaire pour convertir java.sql.Date en LocalDate sans toInstant()
    private LocalDate convertSqlDateToLocalDate(java.sql.Date sqlDate) {
        return sqlDate.toLocalDate();
    }
    public Progression getProgressionById(Long progressionId) {
        return progressionRepository.findById(progressionId)
                .orElseThrow(() -> new EntityNotFoundException("Progression non trouvée avec l'ID : " + progressionId));
    }
    public void updateProgressionStatus(Progression progression) {
        if (progression.getDatedebut() == null) {
            progression.setStatus(ProgressionStatus.NON_COMMENCEE);
        } else if (isProgressionCompleted(progression.getId())) {
            progression.setStatus(ProgressionStatus.TERMINEE);
        } else {
            progression.setStatus(ProgressionStatus.EN_COURS);
        }
    }

    public List<Progression> getAllProgressions() {
        return progressionRepository.findAll();
    }
}
