package com.codecrafter.hitect.services.impl;

import com.codecrafter.hitect.entities.Review;
import com.codecrafter.hitect.exception.ResourceNotFoundException;
import com.codecrafter.hitect.repositories.IReviewRepository;
import com.codecrafter.hitect.services.IReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements IReviewService {

    private final IReviewRepository reviewRepository;

    @CacheEvict(value = "reviews", allEntries = true)
    @Override
    public Review addReview(Review review) {
        review.setAccepted(false);
        review.setDenied(false);
        return reviewRepository.save(review);
    }

    @Cacheable(value = "reviews")
    @Override
    public List<Review> getAllReviews() {
        return reviewRepository.getAllReviews(false, false);
    }

    @CacheEvict(value = "reviews", allEntries = true)
    @Override
    public Boolean deleteReview(Long reviewId) {
        reviewRepository.deleteById(reviewId);
        return true;
    }

    @CacheEvict(value = "reviews", allEntries = true)
    @Override
    public String updateAcceptReview(Long reviewId) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(
                () -> new ResourceNotFoundException("Review not found")
        );
        review.setAccepted(true);
        review.setDenied(false);
        reviewRepository.save(review);
        return "Review accepted and updated!";
    }

    @CacheEvict(value = "reviews", allEntries = true)
    @Override
    public String updateDenyReview(Long reviewId) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(
                () -> new ResourceNotFoundException("Review not found")
        );
        review.setDenied(true);
        review.setAccepted(false);
        reviewRepository.save(review);
        return "Review denied and updated!";
    }

    @Cacheable(value = "reviews", key = "'accepted'", unless = "#result.isEmpty()")
    @Override
    public List<Review> getAcceptedReviews() {
        return reviewRepository.getAllByAccepted(true);
    }

    @Cacheable(value = "reviews", key = "'denied'", unless = "#result.isEmpty()")
    @Override
    public List<Review> getDeniedReviews() {
        return reviewRepository.getAllByDenied(true);
    }
}
