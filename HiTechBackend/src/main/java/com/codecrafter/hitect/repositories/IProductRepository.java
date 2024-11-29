package com.codecrafter.hitect.repositories;

import com.codecrafter.hitect.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
public interface IProductRepository extends JpaRepository<Product, Long> {
}
