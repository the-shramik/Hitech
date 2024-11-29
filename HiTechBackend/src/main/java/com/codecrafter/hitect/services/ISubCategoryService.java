package com.codecrafter.hitect.services;

import com.codecrafter.hitect.entities.SubCategory;
import com.codecrafter.hitect.entities.SubMainCategory;

import java.util.List;

public interface ISubCategoryService {
    SubCategory addSubCategory(SubCategory subCategory);

    List<SubCategory> getSubCategories();

    Boolean updateSubCategory(SubCategory subCategory);

    Boolean deleteSubCategory(Long subCategoryId);

    List<SubCategory> getSubCategoriesBySubMainCategory(Long subMainCategoryId);
}
