package com.codecrafter.hitect.controllers;

import com.codecrafter.hitect.entities.Review;
import com.codecrafter.hitect.services.IReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/review")
@CrossOrigin("*")
public class ReviewController {

    private final IReviewService reviewService;

    @PostMapping("/add-review")
    public ResponseEntity<?> addReview(@RequestBody Review review){
        return ResponseEntity.ok(reviewService.addReview(review));
    }

    @GetMapping("/get-all-reviews")
    public ResponseEntity<?> getAllReviews(){
        return ResponseEntity.ok(reviewService.getAllReviews());
    }

    @DeleteMapping("/delete-review")
    public ResponseEntity<?> deleteReview(@RequestParam Long reviewId){

        boolean isDeleted= reviewService.deleteReview(reviewId);


        if (isDeleted){
            return ResponseEntity.status(HttpStatus.OK).body("Review deleted!");
        }
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Review not deleted!");
        }
    }

    @PutMapping("/update-accept-review")
    public ResponseEntity<?> updateAcceptReview(@RequestParam Long reviewId){

        return ResponseEntity.ok(reviewService.updateAcceptReview(reviewId));
    }

    @PutMapping("/update-deny-review")
    public ResponseEntity<?> updateDenyReview(@RequestParam Long reviewId){

        return ResponseEntity.ok(reviewService.updateDenyReview(reviewId));
    }

    @GetMapping("/get-accepted-reviews")
    public ResponseEntity<?> getAcceptedReviews(){
        return ResponseEntity.ok(reviewService.getAcceptedReviews());
    }
    @GetMapping("/get-denied-reviews")
    public ResponseEntity<?> getDeniedReviews(){
        return ResponseEntity.ok(reviewService.getDeniedReviews());
    }

}
