package com.codecrafter.hitect.services.impl;

import com.codecrafter.hitect.entities.MainCategory;
import com.codecrafter.hitect.entities.SubCategory;
import com.codecrafter.hitect.entities.SubMainCategory;
import com.codecrafter.hitect.exception.ResourceNotFoundException;
import com.codecrafter.hitect.repositories.ISubCategoryRepository;
import com.codecrafter.hitect.services.ISubCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubCategoryServiceImpl implements ISubCategoryService {

    private final ISubCategoryRepository subCategoryRepository;

    @CacheEvict(value = "subcategories", allEntries = true)
    @Override
    public SubCategory addSubCategory(SubCategory subCategory) {
        subCategory.setSubCategoryAddedDate(LocalDate.now());
        return subCategoryRepository.save(subCategory);
    }

    @Cacheable(value = "subcategories")
    @Override
    public List<SubCategory> getSubCategories() {
        return subCategoryRepository.findAll();
    }

    @CacheEvict(value = "subcategories", allEntries = true)
    @Override
    public Boolean updateSubCategory(SubCategory subCategory) {
        SubCategory existedSubCategory=subCategoryRepository.findById(subCategory.getSubCategoryId()).orElseThrow(
                ()->new ResourceNotFoundException("Sub Main category is not present with this details!"));

        existedSubCategory.setSubCategoryName(subCategory.getSubCategoryName());
        existedSubCategory.setSubCategoryAddedDate(existedSubCategory.getSubCategoryAddedDate());
        subCategoryRepository.save(existedSubCategory);
        return true;
    }

    @CacheEvict(value = "subcategories", allEntries = true)
    @Override
    public Boolean deleteSubCategory(Long subCategoryId) {

        subCategoryRepository.deleteById(subCategoryId);
        return true;
    }

    @Override
    public List<SubCategory> getSubCategoriesBySubMainCategory(Long subMainCategoryId) {
        return subCategoryRepository.findBySubMainCategory(subMainCategoryId);
    }
}
