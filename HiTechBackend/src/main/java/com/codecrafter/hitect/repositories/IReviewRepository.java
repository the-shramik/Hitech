package com.codecrafter.hitect.repositories;

import com.codecrafter.hitect.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IReviewRepository extends JpaRepository<Review,Long> {

    @Query(nativeQuery = true,value = "SELECT * FROM reviews WHERE is_accepted=? AND is_denied=?")
    List<Review> getAllReviews(Boolean isAccepted,Boolean isDenied);

    @Query(nativeQuery = true,value = "SELECT * FROM reviews WHERE is_accepted=?")
    List<Review> getAllByAccepted(Boolean isAccepted);

    @Query(nativeQuery = true,value = "SELECT * FROM reviews WHERE is_denied=?")
    List<Review> getAllByDenied(Boolean isDenied);
}
