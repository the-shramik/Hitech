package com.codecrafter.hitect.controllers;

import com.codecrafter.hitect.entities.MainCategory;
import com.codecrafter.hitect.services.IMainCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/main-category")
@RequiredArgsConstructor
@CrossOrigin("*")
public class MainCategoryController {

    private final IMainCategoryService categoryService;

    @PostMapping("/add-main-category")
    public ResponseEntity<?> addMainCategory(@RequestBody MainCategory mainCategory){
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.addMainCategory(mainCategory));
        } catch (Exception e){
            return ResponseEntity.status( HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/get-main-categories")
    public ResponseEntity<?> getMainCategories(){

       return ResponseEntity.ok(categoryService.getAllMainCategories());
    }

    @PutMapping("/update-main-category")
    public ResponseEntity<?> updateMainCategory(@RequestBody MainCategory mainCategory){

        System.out.println(mainCategory.getMainCategoryId());
        System.out.println(mainCategory.getMainCategoryName());
        System.out.println(mainCategory.getMainCategoryAddedDate());
        boolean isUpdated= categoryService.updateMainCategory(mainCategory);

        if (isUpdated){
            return ResponseEntity.status(HttpStatus.OK).body("Main category updated!");
        }
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Main category not updated!");
        }

    }

    @DeleteMapping("/delete-main-category")
    public ResponseEntity<?> deleteMainCategory(@RequestParam Long mainCategoryId){
        boolean isDeleted= categoryService.deleteMainCategory(mainCategoryId);

        if (isDeleted){
            return ResponseEntity.status(HttpStatus.OK).body("Main category deleted!");
        }
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Main category not deleted!");
        }
    }
}
