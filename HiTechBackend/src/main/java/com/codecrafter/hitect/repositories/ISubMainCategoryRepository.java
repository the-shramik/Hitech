package com.codecrafter.hitect.repositories;

import com.codecrafter.hitect.entities.SubMainCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ISubMainCategoryRepository extends JpaRepository<SubMainCategory,Long> {


    @Query(nativeQuery = true,value = "SELECT * FROM submaincategories WHERE main_category_id=?")
    List<SubMainCategory> findByMainCategory(Long mainCategoryId);
}
