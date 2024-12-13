package com.codecrafter.hitect.controllers;

import com.codecrafter.hitect.entities.SubMainCategory;
import com.codecrafter.hitect.services.ISubMainCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sub-main-category")
@RequiredArgsConstructor
@CrossOrigin("*")
public class SubMainCategoryController {
    private final ISubMainCategoryService subMainCategoryService;

    @PostMapping("/add-sub-main-category")
    public ResponseEntity<?> addSubMainCategory(@RequestBody SubMainCategory subMainCategory){

        System.out.println(subMainCategory.getMainCategory().getMainCategoryId());
        try {
            System.out.println("fine");
            return ResponseEntity.status(HttpStatus.CREATED).body(subMainCategoryService.addSubMainCategory(subMainCategory));
        } catch (Exception e){
            System.out.println("jhgh");
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/get-sub-main-categories")
    public ResponseEntity<?> getSubMainCategories(){


        return ResponseEntity.ok(subMainCategoryService.getAllSubMainCategories());
    }


    @PutMapping("/update-sub-main-category")
    public ResponseEntity<?> updateSubMainCategory(@RequestBody SubMainCategory subMainCategory){

        System.out.println(subMainCategory.getSubMainCategoryId());
        System.out.println(subMainCategory.getSubMainCategoryName());
        boolean isUpdated= subMainCategoryService.updateSubMainCategory(subMainCategory);


        if (isUpdated){
            return ResponseEntity.status(HttpStatus.OK).body("SubMain category updated!");
        }
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("SubMain category not updated!");
        }

    }

    @DeleteMapping("/delete-sub-main-category")
    public ResponseEntity<?> deleteSubMainCategory(@RequestParam Long subMainCategoryId){

        System.out.println("delete api works");
        boolean isDeleted= subMainCategoryService.deleteSubMainCategory(subMainCategoryId);

        if (isDeleted){
            return ResponseEntity.status(HttpStatus.OK).body("SubMain category deleted!");
        }
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("SubMain category not deleted!");
        }
    }

    @GetMapping("/get-sub-main-categories-by-main-category")
    public ResponseEntity<?> getSubMainCategoriesByMainCategory(@RequestParam Long mainCategoryId){

        System.out.println("Sub main category working");
        System.out.println(mainCategoryId);
        System.out.println(subMainCategoryService.getSubMainCategoriesByMainCategory(mainCategoryId));
        return ResponseEntity.ok(subMainCategoryService.getSubMainCategoriesByMainCategory(mainCategoryId));
    }
}
