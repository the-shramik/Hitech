package com.codecrafter.hitect.repositories;

import com.codecrafter.hitect.entities.SubCategory;
import com.codecrafter.hitect.entities.SubMainCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ISubCategoryRepository extends JpaRepository<SubCategory, Long> {

    @Query(nativeQuery = true,value = "SELECT * FROM subcategories WHERE sub_main_category_id=?")
    List<SubCategory> findBySubMainCategory(Long subMainCategoryId);
}
