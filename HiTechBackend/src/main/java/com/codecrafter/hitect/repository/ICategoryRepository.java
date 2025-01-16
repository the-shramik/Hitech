package com.codecrafter.hitect.repository;

import com.codecrafter.hitect.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ICategoryRepository extends JpaRepository<Category,Long> {

    @Query(nativeQuery = true,value = "SELECT main_category_name FROM category ")
    Set<String> getAllMainCategories();

    @Query(nativeQuery = true,value = "SELECT sub_main_category_name FROM category ")
    Set<String> getAllSubMainCategories();

    @Query(nativeQuery = true,value = "SELECT sub_category_name FROM category ")
    Set<String> getAllSubCategories();

    @Query(nativeQuery = true,value ="SELECT sub_main_category_name FROM category WHERE main_category_name=?")
    Set<String> getAllSubMainCategoriesByMainCategory(String mainCategory);

    @Query(nativeQuery = true,value ="SELECT sub_category_name FROM category WHERE sub_main_category_name=?")
    Set<String> getAllSubCategoriesBySubMainCategory(String subMainCategory);
}
