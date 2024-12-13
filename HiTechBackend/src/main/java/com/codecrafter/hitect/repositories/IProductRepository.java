package com.codecrafter.hitect.repositories;

import com.codecrafter.hitect.entities.Product;
import com.codecrafter.hitect.entities.dtos.ProductDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Repository
public interface IProductRepository extends JpaRepository<Product, Long> {

    @Query(nativeQuery = true,value = "SELECT * FROM product WHERE sub_main_category_id=?")
    List<Product> findBySubMainCategory(Long subMainCategoryId);

    @Query(nativeQuery = true,value = "SELECT * FROM product WHERE sub_category_id=?")
    List<Product> findBySubCategory(Long subCategoryId);

    @Query("SELECT p FROM Product p " +
            "JOIN p.subMainCategory smc " +
            "JOIN smc.mainCategory mc " +
            "WHERE mc.mainCategoryId = :mainCategoryId")
    List<Product> findAllProductsByMainCategoryId(@Param("mainCategoryId") Long mainCategoryId);
}
