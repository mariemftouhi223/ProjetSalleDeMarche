package tn.esprit.projetsalledemarche.Service.Servicelinda.IMP;

import tn.esprit.projetsalledemarche.dto.ReviewDto;

import java.util.List;

public interface ReviewService {
    // Créer un avis pour une formation spécifique
    ReviewDto createReview(Long formationId, ReviewDto reviewDto);

    // Obtenir tous les avis pour une formation spécifique
    List<ReviewDto> getReviewsByFormationId(Long formationId);

    // Obtenir un avis spécifique par ID et ID de formation
    ReviewDto getReviewById(Long reviewId, Long formationId);

    // Mettre à jour un avis spécifique pour une formation donnée
    ReviewDto updateReview(Long formationId, Long reviewId, ReviewDto reviewDto);

    // Supprimer un avis spécifique pour une formation donnée
    void deleteReview(Long formationId, Long reviewId);
}
