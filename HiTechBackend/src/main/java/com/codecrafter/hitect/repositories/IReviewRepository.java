package com.codecrafter.hitect.repositories;

import com.codecrafter.hitect.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IReviewRepository extends JpaRepository<Review,Long> {
}
