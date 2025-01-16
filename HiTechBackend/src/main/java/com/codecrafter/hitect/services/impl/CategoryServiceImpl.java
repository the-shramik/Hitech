package com.codecrafter.hitect.services.impl;

import com.codecrafter.hitect.entities.Category;
import com.codecrafter.hitect.exception.ResourceNotFoundException;
import com.codecrafter.hitect.repository.ICategoryRepository;
import com.codecrafter.hitect.services.ICategoryService;
import lombok.RequiredArgsConstructor;
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
        category.setMainCategoryName(category.getMainCategoryName().toUpperCase());
        category.setSubMainCategoryName(category.getSubMainCategoryName().toUpperCase());
        category.setSubCategoryName(category.getSubCategoryName().toUpperCase());
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
            existedCategory.setMainCategoryName(category.getMainCategoryName().toUpperCase());
        }else{
            existedCategory.setMainCategoryName(existedCategory.getMainCategoryName());
        }

        if(category.getSubMainCategoryName()!=null){
            existedCategory.setSubMainCategoryName(category.getSubMainCategoryName().toUpperCase());
        }else{
            existedCategory.setSubMainCategoryName(existedCategory.getSubMainCategoryName());
        }

        if(category.getSubCategoryName()!=null){
            existedCategory.setSubCategoryName(existedCategory.getSubCategoryName().toUpperCase());
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

    @Override
    public Set<String> getSubMainCategoriesByMainCategory(String mainCategory) {
        return  categoryRepository.getAllSubMainCategoriesByMainCategory(mainCategory.toUpperCase());

    }

    @Override
    public Set<String> getSubCategoriesBySubMainCategory(String subMainCategory) {
        return categoryRepository.getAllSubCategoriesBySubMainCategory(subMainCategory.toUpperCase());
    }


}
