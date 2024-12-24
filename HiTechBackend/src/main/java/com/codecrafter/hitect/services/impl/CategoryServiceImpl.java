package com.codecrafter.hitect.services.impl;

import com.codecrafter.hitect.entities.Category;
import com.codecrafter.hitect.exception.ResourceNotFoundException;
import com.codecrafter.hitect.repository.ICategoryRepository;
import com.codecrafter.hitect.services.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements ICategoryService {

    private final ICategoryRepository categoryRepository;

    @Override
    public Category addCategory(Category category) {
        category.setCategoryAddedDate(LocalDate.now());
        return categoryRepository.save(category);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Boolean updateCategory(Category category) {
        Category existedCategory=categoryRepository.findById(category.getCategoryId()).orElseThrow(
                ()->new ResourceNotFoundException("Category is not present with this details!"));

        if(category.getMainCategoryName()!=null){
            existedCategory.setMainCategoryName(category.getMainCategoryName());
        }else{
            existedCategory.setMainCategoryName(existedCategory.getMainCategoryName());
        }

        if(category.getSubMainCategoryName()!=null){
            existedCategory.setSubMainCategoryName(category.getSubMainCategoryName());
        }else{
            existedCategory.setSubMainCategoryName(existedCategory.getSubMainCategoryName());
        }

        if(category.getSubCategoryName()!=null){
            existedCategory.setSubCategoryName(existedCategory.getSubCategoryName());
        }else{
            existedCategory.setSubCategoryName(existedCategory.getSubCategoryName());
        }

        categoryRepository.save(existedCategory);
        return true;
    }

    @Override
    public Boolean deleteCategory(Long categoryId) {

        categoryRepository.deleteById(categoryId);
        return true;
    }

    @Override
    public Set<String> getMainCategories() {
       return categoryRepository.getAllMainCategories();
    }

    @Override
    public Set<String> getSubMainCategories() {
        return categoryRepository.getAllSubMainCategories();
    }

    @Override
    public Set<String> getSubCategories() {
        return categoryRepository.getAllSubCategories();
    }


}
