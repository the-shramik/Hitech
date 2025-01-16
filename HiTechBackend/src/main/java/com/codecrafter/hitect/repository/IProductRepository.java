package com.codecrafter.hitect.repository;

import com.codecrafter.hitect.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProductRepository extends JpaRepository<Product,Long> {

    List<Product> findAllByMainCategoryName(String mainCategoryName);

    List<Product> findAllBySubMainCategoryName(String subMainCategoryName);

    List<Product> findAllBySubCategoryName(String subCategory);

}
