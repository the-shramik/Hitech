package com.codecrafter.hitect.repository;

import com.codecrafter.hitect.entities.SpecialOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISpecialOfferRepository extends JpaRepository<SpecialOffer,Long> {
}
