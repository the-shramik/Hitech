package com.codecrafter.hitect.services.impl;

import com.codecrafter.hitect.entities.SubCategory;
import com.codecrafter.hitect.entities.SubMainCategory;
import com.codecrafter.hitect.exception.ResourceNotFoundException;
import com.codecrafter.hitect.repositories.ISubMainCategoryRepository;
import com.codecrafter.hitect.services.ISubMainCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubMainCategoryServiceImpl implements ISubMainCategoryService {

    private final ISubMainCategoryRepository subMainCategoryRepository;

    @CacheEvict(value = "submaincategories", allEntries = true)
    @Override
    public SubMainCategory addSubMainCategory(SubMainCategory subMainCategory) {
        subMainCategory.setSubMainCategoryAddedDate(LocalDate.now());
        return subMainCategoryRepository.save(subMainCategory);
    }

    @Cacheable(value = "submaincategories")
    @Override
    public List<SubMainCategory> getAllSubMainCategories() {
        return subMainCategoryRepository.findAll();
    }

    @CacheEvict(value = "submaincategories", allEntries = true)
    @Override
    public Boolean updateSubMainCategory(SubMainCategory subMainCategory) {
        SubMainCategory existedSubMainCategory=subMainCategoryRepository.findById(subMainCategory.getSubMainCategoryId()).orElseThrow(
                ()->new ResourceNotFoundException("Main category is not present with this details!"));

        existedSubMainCategory.setSubMainCategoryName(subMainCategory.getSubMainCategoryName());
        existedSubMainCategory.setSubMainCategoryAddedDate(existedSubMainCategory.getSubMainCategoryAddedDate());
        SubMainCategory savedCat = subMainCategoryRepository.save(existedSubMainCategory);
        System.out.println(savedCat.getSubMainCategoryName());
        return true;
    }

    @CacheEvict(value = "submaincategories", allEntries = true)
    @Override
    public Boolean deleteSubMainCategory(Long subMainCategoryId) {

        System.out.println(subMainCategoryId);
        subMainCategoryRepository.deleteById(subMainCategoryId);
        return true;
    }

    @Override
    public List<SubMainCategory> getSubMainCategoriesByMainCategory(Long mainCategoryId) {
        return subMainCategoryRepository.findByMainCategory(mainCategoryId);
    }
}
