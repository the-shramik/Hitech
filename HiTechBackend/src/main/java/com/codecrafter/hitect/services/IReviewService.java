package com.codecrafter.hitect.services;

import com.codecrafter.hitect.entities.Review;

import java.util.List;

public interface IReviewService {

    Review addReview(Review review);

    List<Review> getAllReviews();

    Boolean deleteReview(Long reviewId);
}