package tn.esprit.projetsalledemarche.Service.Servicelinda.ser.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.projetsalledemarche.Entity.Linda.user.Formation;
import tn.esprit.projetsalledemarche.Entity.Linda.user.Review;
import tn.esprit.projetsalledemarche.Repository.lindarepo.formation.FormationRepository;
import tn.esprit.projetsalledemarche.Repository.lindarepo.user.ReviewRepository;
import tn.esprit.projetsalledemarche.Service.Servicelinda.IMP.ReviewService;
import tn.esprit.projetsalledemarche.dto.ReviewDto;
import tn.esprit.projetsalledemarche.exceptions.FormationNotFoundException;
import tn.esprit.projetsalledemarche.exceptions.ReviewNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final FormationRepository formationRepository;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository, FormationRepository formationRepository) {
        this.reviewRepository = reviewRepository;
        this.formationRepository = formationRepository;
    }

    @Override
    public ReviewDto createReview(Long formationId, ReviewDto reviewDto) {
        Review review = mapToEntity(reviewDto);

        Formation formation = formationRepository.findById(formationId)
                .orElseThrow(() -> new FormationNotFoundException("Formation with associated review not found"));

        review.setFormation(formation);

        Review newReview = reviewRepository.save(review);

        return mapToDto(newReview);
    }

    @Override
    public List<ReviewDto> getReviewsByFormationId(Long formationId) {
        List<Review> reviews = reviewRepository.findByFormationId(formationId);

        return reviews.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public ReviewDto getReviewById(Long reviewId, Long formationId) {
        Formation formation = formationRepository.findById(formationId)
                .orElseThrow(() -> new FormationNotFoundException("Formation with associated review not found"));

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewNotFoundException("Review with associated formation not found"));

        if (review.getFormation().getId() != formation.getId()) {
            throw new ReviewNotFoundException("This review does not belong to this formation");
        }

        return mapToDto(review);
    }

    @Override
    public ReviewDto updateReview(Long formationId, Long reviewId, ReviewDto reviewDto) {
        Formation formation = formationRepository.findById(formationId)
                .orElseThrow(() -> new FormationNotFoundException("Formation with associated review not found"));

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewNotFoundException("Review with associated formation not found"));

        if (review.getFormation().getId() != formation.getId()) {
            throw new ReviewNotFoundException("This review does not belong to this formation");
        }

        review.setTitle(reviewDto.getTitle());
        review.setContent(reviewDto.getContent());
        review.setStars(reviewDto.getStars());

        Review updatedReview = reviewRepository.save(review);

        return mapToDto(updatedReview);
    }

    @Override
    public void deleteReview(Long formationId, Long reviewId) {
        Formation formation = formationRepository.findById(formationId)
                .orElseThrow(() -> new FormationNotFoundException("Formation with associated review not found"));

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewNotFoundException("Review with associated formation not found"));

        if (review.getFormation().getId() != formation.getId()) {
            throw new ReviewNotFoundException("This review does not belong to this formation");
        }

        reviewRepository.delete(review);
    }
    private ReviewDto mapToDto(Review review) {
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setId(review.getId());
        reviewDto.setTitle(review.getTitle());
        reviewDto.setContent(review.getContent());
        reviewDto.setStars(review.getStars());
        return reviewDto;
    }

    private Review mapToEntity(ReviewDto reviewDto) {
        Review review = new Review();
        review.setId(reviewDto.getId());
        review.setTitle(reviewDto.getTitle());
        review.setContent(reviewDto.getContent());
        review.setStars(reviewDto.getStars());
        return review;
    }
}
