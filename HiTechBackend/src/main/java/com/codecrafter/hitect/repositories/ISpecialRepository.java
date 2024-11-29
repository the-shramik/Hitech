package com.codecrafter.hitect.repositories;

import com.codecrafter.hitect.entities.SpecialOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISpecialRepository extends JpaRepository<SpecialOffer,Long> {
}
