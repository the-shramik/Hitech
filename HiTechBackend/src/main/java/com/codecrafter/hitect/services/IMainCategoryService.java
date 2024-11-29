package com.codecrafter.hitect.services;

import com.codecrafter.hitect.entities.MainCategory;

import java.util.List;

public interface IMainCategoryService {

    MainCategory addMainCategory(MainCategory mainCategory);

    List<MainCategory> getAllMainCategories();

    Boolean updateMainCategory(MainCategory mainCategory);

    Boolean deleteMainCategory(Long mainCategoryId);
}
