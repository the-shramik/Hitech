package com.codecrafter.hitect.services;

import com.codecrafter.hitect.entities.Product;
import com.codecrafter.hitect.entities.SubCategory;
import com.codecrafter.hitect.entities.SubMainCategory;
import com.codecrafter.hitect.entities.dtos.ProductDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface IProductService {
    Map<String, Object> addProduct(String productName, List<MultipartFile> imageFiles, Long subMainCategoryId, Long subCategoryId) throws IOException;

    List<ProductDto> getAllProducts();

    Map<String, Object> updateProduct(Long productId,String productName, List<MultipartFile> imageFiles, Long subMainCategoryId, Long subCategoryId) throws IOException;


    Boolean deleteProduct(Long productId);
}
