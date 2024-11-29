package com.codecrafter.hitect.services.impl;

import com.codecrafter.hitect.entities.Review;
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
        return reviewRepository.save(review);
    }

    @Cacheable(value = "reviews")
    @Override
    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    @CacheEvict(value = "reviews", allEntries = true)
    @Override
    public Boolean deleteReview(Long reviewId) {

        reviewRepository.deleteById(reviewId);
        return true;
    }
}
