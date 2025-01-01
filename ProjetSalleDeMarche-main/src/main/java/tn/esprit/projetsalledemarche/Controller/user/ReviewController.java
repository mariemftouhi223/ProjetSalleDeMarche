package tn.esprit.projetsalledemarche.Controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.projetsalledemarche.Entity.Linda.user.Review;
import tn.esprit.projetsalledemarche.Service.user.ReviewServiceImpl;

import java.util.Map;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewServiceImpl reviewServiceImpl;

    @Autowired
    public ReviewController(ReviewServiceImpl reviewServiceImpl) {
        this.reviewServiceImpl = reviewServiceImpl;
    }

    // Ajouter un avis
    @PostMapping
    public ResponseEntity<Review> addReview(@RequestBody Review review) {
        Review savedReview = reviewServiceImpl.addReview(review);
        return ResponseEntity.ok(savedReview);
    }

    // Obtenir le sentiment associé à un avis
    @GetMapping("/{id}/sentiment")
    public ResponseEntity<String> getReviewSentiment(@PathVariable("id") Long reviewId) {
        // Récupérer l'avis depuis le service
        Review review = reviewServiceImpl.getReviewById(reviewId);

        // Vérifier si l'avis existe
        if (review == null) {
            return ResponseEntity.notFound().build(); // Si l'avis n'est pas trouvé, retourner 404
        }

        // Retourner le sentiment de l'avis
        return ResponseEntity.ok(review.getSentiment());
    }
    @GetMapping("/sentiment-percentage")
    public Map<String, Double> getSentimentPercentage() {
        return reviewServiceImpl.calculateSentimentPercentage();
    }
}