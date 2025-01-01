package tn.esprit.projetsalledemarche.Service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.projetsalledemarche.Entity.Linda.user.Review;
import tn.esprit.projetsalledemarche.Repository.lindarepo.user.ReviewRepository;
import tn.esprit.projetsalledemarche.Service.SentimentService;

import java.util.List;
import java.util.Map;

@Service
public class ReviewServiceImpl {

    private final ReviewRepository reviewRepository;
    private final SentimentService sentimentService;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository, SentimentService sentimentService) {
        this.reviewRepository = reviewRepository;
        this.sentimentService = sentimentService;
    }

    // Ajouter un avis
    public Review addReview(Review review) {
        // Analyser le sentiment du contenu
        String sentiment = sentimentService.analyzeSentiment(review.getContent());
        review.setSentiment(sentiment); // Définir le sentiment de l'avis

        // Sauvegarder l'avis dans la base de données
        return reviewRepository.save(review);
    }

    // Récupérer un avis par son ID
    public Review getReviewById(Long reviewId) {
        return reviewRepository.findById(reviewId).orElse(null); // Retourner l'avis ou null si non trouvé
    }
    public Map<String, Double> calculateSentimentPercentage() {
        List<Review> reviews = reviewRepository.findAll();

        if (reviews.isEmpty()) {
            return Map.of("positive", 0.0, "negative", 0.0);
        }

        long positiveCount = reviews.stream()
                .filter(review -> "POSITIVE".equalsIgnoreCase(review.getSentiment()))
                .count();

        long negativeCount = reviews.stream()
                .filter(review -> "NEGATIVE".equalsIgnoreCase(review.getSentiment()))
                .count();

        double total = reviews.size();
        double positivePercentage = (positiveCount / total) * 100;
        double negativePercentage = (negativeCount / total) * 100;

        return Map.of(
                "positive", positivePercentage,
                "negative", negativePercentage
        );
    }
}
