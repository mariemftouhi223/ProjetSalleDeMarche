package tn.esprit.projetsalledemarche.Controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.projetsalledemarche.Service.Servicelinda.IMP.ReviewService;
import tn.esprit.projetsalledemarche.dto.ReviewDto;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class ReviewController {
    private ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/formation/{formationId}/reviews")
    public ResponseEntity<ReviewDto> createReview(@PathVariable(value = "formationId") Long formationId, @RequestBody ReviewDto reviewDto) {
        return new ResponseEntity<>(reviewService.createReview(formationId, reviewDto), HttpStatus.CREATED);
    }

    @GetMapping("/formation/{formationId}/reviews")
    public List<ReviewDto> getReviewsByFormationId(@PathVariable(value = "formationId") Long formationId) {
        return reviewService.getReviewsByFormationId(formationId);
    }

    @GetMapping("/formation/{formationId}/reviews/{id}")
    public ResponseEntity<ReviewDto> getReviewById(@PathVariable(value = "formationId") Long formationId, @PathVariable(value = "id") Long reviewId) {
        ReviewDto reviewDto = reviewService.getReviewById(formationId, reviewId);
        return new ResponseEntity<>(reviewDto, HttpStatus.OK);
    }

    @PutMapping("/formation/{formationId}/reviews/{id}")
    public ResponseEntity<ReviewDto> updateReview(@PathVariable(value = "formationId") Long formationId, @PathVariable(value = "id") Long reviewId,
                                                  @RequestBody ReviewDto reviewDto) {
        ReviewDto updatedReview = reviewService.updateReview(formationId, reviewId, reviewDto);
        return new ResponseEntity<>(updatedReview, HttpStatus.OK);
    }

    @DeleteMapping("/formation/{formationId}/reviews/{id}")
    public ResponseEntity<String> deleteReview(@PathVariable(value = "formationId") Long formationId, @PathVariable(value = "id") Long reviewId) {
        reviewService.deleteReview(formationId, reviewId);
        return new ResponseEntity<>("Review deleted successfully", HttpStatus.OK);
    }
}
