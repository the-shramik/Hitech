package com.codecrafter.hitect.services;

import com.codecrafter.hitect.entities.Category;

import java.util.List;
import java.util.Set;

public interface ICategoryService {

    Category addCategory(Category category);

    List<Category> getAllCategories();

    Boolean updateCategory(Category category);

    Boolean deleteCategory(Long categoryId);

    Set<String> getMainCategories();

    Set<String> getSubMainCategories();

    Set<String> getSubCategories();

    Set<String> getSubMainCategoriesByMainCategory(String mainCategory);
    Set<String> getSubCategoriesBySubMainCategory(String subMainCategory);
}
