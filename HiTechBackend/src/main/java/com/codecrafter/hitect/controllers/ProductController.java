package com.codecrafter.hitect.controllers;


import com.codecrafter.hitect.entities.dtos.ProductDto;
import com.codecrafter.hitect.exception.ResourceNotFoundException;
import com.codecrafter.hitect.services.IProductService;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ProductController {

    private final IProductService productService;

    @PostMapping("/add-product")
    public ResponseEntity<?> addProduct(
            @RequestParam("productName") String productName,
            @RequestParam("productImages") List<MultipartFile> productImages,
            @RequestParam(value = "subMainCategoryId", required = false) @Nullable Long subMainCategoryId,
            @RequestParam(value = "subCategoryId", required = false) @Nullable Long subCategoryId
            ) throws IOException {
        try {
            // Upload product with multiple images
            Map<String, Object> response = productService.addProduct(productName, productImages,subMainCategoryId,subCategoryId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(500).body("Error uploading product or images: " + e.getMessage());
        }
    }

    @GetMapping("/get-products")
    public ResponseEntity<?> getProducts() {
        try {
            List<ProductDto> products = productService.getAllProducts();
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(500).body("Error getting products: " + e.getMessage());
        }
    }

    @PutMapping("/update-product")
    public ResponseEntity<?> updateProduct(
            @RequestParam("productId") Long productId,
            @RequestParam("productName") String productName,
            @RequestParam("imageUrls")@Nullable List<MultipartFile> imageUrls,
            @RequestParam(value = "subMainCategoryId", required = false) @Nullable Long subMainCategoryId,
            @RequestParam(value = "subCategoryId", required = false) @Nullable Long subCategoryId
    ) throws IOException {

        System.out.println("request coming");
        try {
            // Upload product with multiple images
            Map<String, Object> response = productService.updateProduct(productId,productName, imageUrls,subMainCategoryId,subCategoryId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(500).body("Error updating product or images: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete-product")
    public ResponseEntity<?> deleteProduct(@RequestParam Long productId){
        try {
            boolean isDeleted = productService.deleteProduct(productId);
            Map<String, Object> response = new HashMap<>();
            response.put("success", isDeleted);
            response.put("message", "Product and associated images deleted successfully.");
            return ResponseEntity.ok(response);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("success", false, "message", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("success", false, "message", e.getMessage()));
        }
    }

    @GetMapping("/get-products-by-sub-main-category")
    public ResponseEntity<?> getProductsBySubMainCategory(@RequestParam Long subMainCategoryId) {
        try {
            List<ProductDto> products = productService.getAllProductsBySubMainCategory(subMainCategoryId);
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error getting products: " + e.getMessage());
        }
    }


    @GetMapping("/get-products-by-sub-category")
    public ResponseEntity<?> getProductsBySubCategory(@RequestParam Long subCategoryId) {
        try {
            List<ProductDto> products = productService.getAllProductsBySubCategory(subCategoryId);
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error getting products: " + e.getMessage());
        }
    }

    @GetMapping("/get-products-by-main-category")
    public ResponseEntity<?> getProductsByMainCategory(@RequestParam Long mainCategoryId) {
        try {
            List<ProductDto> products = productService.getAllProductsByMainCategory(mainCategoryId);
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error getting products: " + e.getMessage());
        }
    }
}
