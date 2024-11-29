package com.codecrafter.hitect.services.impl;

import com.codecrafter.hitect.entities.MainCategory;
import com.codecrafter.hitect.exception.ResourceNotFoundException;
import com.codecrafter.hitect.repositories.IMainCategoryRepository;
import com.codecrafter.hitect.services.IMainCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MainCategoryServiceImpl implements IMainCategoryService {

    private final IMainCategoryRepository mainCategoryRepository;

    @CacheEvict(value = "maincategories", allEntries = true)
    @Override
    public MainCategory addMainCategory(MainCategory mainCategory) {
        mainCategory.setMainCategoryAddedDate(LocalDate.now());
        return mainCategoryRepository.save(mainCategory);
    }

    @Cacheable(value = "maincategories")
    @Override
    public List<MainCategory> getAllMainCategories() {
        return mainCategoryRepository.findAll();
    }

    @CacheEvict(value = "maincategories", allEntries = true)
    @Override
    public Boolean updateMainCategory(MainCategory mainCategory) {
        MainCategory existedMainCategory=mainCategoryRepository.findById(mainCategory.getMainCategoryId()).orElseThrow(
                ()->new ResourceNotFoundException("Main category is not present with this details!"));

        existedMainCategory.setMainCategoryName(mainCategory.getMainCategoryName());
        existedMainCategory.setMainCategoryAddedDate(existedMainCategory.getMainCategoryAddedDate());
        mainCategoryRepository.save(existedMainCategory);
        return true;
    }

    @CacheEvict(value = "maincategories", allEntries = true)
    @Override
    public Boolean deleteMainCategory(Long mainCategoryId) {

        mainCategoryRepository.deleteById(mainCategoryId);
        return true;
    }


}
