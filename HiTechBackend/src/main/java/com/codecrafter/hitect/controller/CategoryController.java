package com.codecrafter.hitect.controller;

import com.codecrafter.hitect.entities.Category;
import com.codecrafter.hitect.services.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
@CrossOrigin("*")
public class CategoryController {
    private final ICategoryService categoryService;

    @PostMapping("/add-category")
    public ResponseEntity<?> addCategory(@RequestBody Category category){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(categoryService.addCategory(category));
        }catch (Exception e){
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/get-categories")
    public ResponseEntity<?> getAllCategories(){
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @PutMapping("/update-category")
    public ResponseEntity<?> updateCategory(@RequestBody Category category){
       try {
           return ResponseEntity.status(HttpStatus.OK).body(categoryService.updateCategory(category));
       }catch (Exception e){
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
       }
    }

    @DeleteMapping("/delete-category")
    public ResponseEntity<?> deleteCategory(@RequestParam Long categoryId){
           return ResponseEntity.ok(categoryService.deleteCategory(categoryId));
    }

    @GetMapping("/get-main-categories")
    public ResponseEntity<?> getMainCategories(){
       return ResponseEntity.ok(categoryService.getMainCategories());
    }

    @GetMapping("/get-sub-main-categories")
    public ResponseEntity<?> getSubMainCategories(){
       return ResponseEntity.ok(categoryService.getSubMainCategories());
    }

    @GetMapping("/get-sub-categories")
    public ResponseEntity<?> getSubCategories(){
        return ResponseEntity.ok(categoryService.getSubCategories());
    }

}
