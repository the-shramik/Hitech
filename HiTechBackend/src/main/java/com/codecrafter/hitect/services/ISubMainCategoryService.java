package com.codecrafter.hitect.services;

import com.codecrafter.hitect.entities.MainCategory;
import com.codecrafter.hitect.entities.SubMainCategory;

import java.util.List;

public interface ISubMainCategoryService {
    SubMainCategory addSubMainCategory(SubMainCategory subMainCategory);

    List<SubMainCategory> getAllSubMainCategories();

    Boolean updateSubMainCategory(SubMainCategory subMainCategory);

    Boolean deleteSubMainCategory(Long subMainCategoryId);

    List<SubMainCategory> getSubMainCategoriesByMainCategory(Long mainCategoryId);
}
