package com.codecrafter.hitect.controller;

import com.codecrafter.hitect.services.IProductService;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ProductController {
    private final IProductService productService;

    @PostMapping("/add-product")
    public ResponseEntity<?> addProduct(@RequestParam("productName") String productName,
                                        @RequestParam("productDescription") String productDescription,
                                        @RequestParam("imageFiles") List<MultipartFile> imageFiles,
                                        @RequestParam("mainCategoryName") String mainCategoryName,
                                        @RequestParam("subMainCategoryName") String subMainCategoryName,
                                        @RequestParam(value = "subCategoryName", required = false) String subCategoryName) {

        try {
            return ResponseEntity.status(HttpStatus.OK).body(productService.addProduct(productName, productDescription, imageFiles, mainCategoryName, subMainCategoryName, subCategoryName));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/get-products")
    public ResponseEntity<?> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @PutMapping("/update-product")
    public ResponseEntity<?> updateProduct(@RequestParam("productId") Long productId,
                                           @RequestParam("productDescription") String productDescription,
                                           @RequestParam("productName") String productName,
                                           @RequestParam(value = "imageFiles", required = false)@Nullable List<MultipartFile> imageFiles,
                                           @RequestParam("mainCategoryName") String mainCategoryName,
                                           @RequestParam("subMainCategoryName") String subMainCategoryName,
                                           @RequestParam("subCategoryName") String subCategoryName) throws IOException {


            System.out.println(mainCategoryName+"=>"+subMainCategoryName+"=>"+subCategoryName);
            return ResponseEntity.ok(productService.updateProduct(productId, productName, productDescription, imageFiles, mainCategoryName, subMainCategoryName, subCategoryName));

    }

    @DeleteMapping("/delete-product")
    public ResponseEntity<?> deleteProduct(@RequestParam Long productId) {
            return ResponseEntity.ok(productService.deleteProduct(productId));

    }


    @GetMapping("/get-product-by-id")
    public ResponseEntity<?> getProductById(@RequestParam Long productId) {
        return ResponseEntity.ok(productService.getProductById(productId));
    }
}
