//package tn.esprit.projetsalledemarche.Service.ser.user;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import tn.esprit.projetsalledemarche.Entity.Linda.user.Review;
//import tn.esprit.projetsalledemarche.Repository.lindarepo.formation.FormationRepository;
//import tn.esprit.projetsalledemarche.Repository.lindarepo.user.ReviewRepository;
//import tn.esprit.projetsalledemarche.Service.IMP.ReviewService;
//import tn.esprit.projetsalledemarche.Service.ser.SentimentService;
//
//@Service
//public class ReviewServiceImpl {
//
//    private final ReviewRepository reviewRepository;
//    private final SentimentService sentimentService;
//
//    @Autowired
//    public ReviewServiceImpl(ReviewRepository reviewRepository, SentimentService sentimentService) {
//        this.reviewRepository = reviewRepository;
//        this.sentimentService = sentimentService;
//    }
//
//    // Ajouter un avis
//    public Review addReview(Review review) {
//        // Analyser le sentiment du contenu
//        String sentiment = sentimentService.analyzeSentiment(review.getContent());
//        review.setSentiment(sentiment); // Définir le sentiment de l'avis
//
//        // Sauvegarder l'avis dans la base de données
//        return reviewRepository.save(review);
//    }
//
//    // Récupérer un avis par son ID
//    public Review getReviewById(Long reviewId) {
//        return reviewRepository.findById(reviewId).orElse(null); // Retourner l'avis ou null si non trouvé
//    }
//}
