package com.codecrafter.hitect.controllers;

import com.codecrafter.hitect.entities.SubCategory;
import com.codecrafter.hitect.services.ISubCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sub-category")
@RequiredArgsConstructor
@CrossOrigin("*")
public class SubCategoryController {

    private final ISubCategoryService subCategoryService;

    @PostMapping("/add-sub-category")
    public ResponseEntity<?> addSubCategory(@RequestBody SubCategory subCategory){

        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(subCategoryService.addSubCategory(subCategory));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/get-sub-categories")
    public ResponseEntity<?> getSubCategories(){
        return ResponseEntity.ok(subCategoryService.getSubCategories());
    }

    @PutMapping("/update-sub-category")
    public ResponseEntity<?> updateMainCategory(@RequestBody SubCategory subCategory){

        System.out.println(subCategory.getSubCategoryId());
        System.out.println(subCategory.getSubCategoryName());

        boolean isUpdated= subCategoryService.updateSubCategory(subCategory);

        if (isUpdated){
            return ResponseEntity.status(HttpStatus.OK).body("Sub category updated!");
        }
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Sub category not updated!");
        }

    }

    @DeleteMapping("/delete-sub-category")
    public ResponseEntity<?> deleteSubCategory(@RequestParam Long subCategoryId){
        boolean isDeleted= subCategoryService.deleteSubCategory(subCategoryId);

        if (isDeleted){
            return ResponseEntity.status(HttpStatus.OK).body("Sub category deleted!");
        }
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Sub category not deleted!");
        }
    }

    @GetMapping("/get-sub-categories-by-sub-main-categories")
    public ResponseEntity<?> getSubCategoriesBySubMainCategories(@RequestParam Long subMainCategoryId){
        return ResponseEntity.ok(subCategoryService.getSubCategoriesBySubMainCategory(subMainCategoryId));
    }
}
