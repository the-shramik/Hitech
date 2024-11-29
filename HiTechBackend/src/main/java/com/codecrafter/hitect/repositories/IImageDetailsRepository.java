package com.codecrafter.hitect.repositories;

import com.codecrafter.hitect.entities.ImageDetails;
import com.codecrafter.hitect.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IImageDetailsRepository extends JpaRepository<ImageDetails, Long> {

    List<ImageDetails> findAllByProduct(Product product);

    List<ImageDetails> findByProduct(Product product);
}
