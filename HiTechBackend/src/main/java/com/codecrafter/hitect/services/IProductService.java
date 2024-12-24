package com.codecrafter.hitect.services;

import com.codecrafter.hitect.entities.dtos.ProductDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface IProductService {
    Map<String, Object> addProduct(String productName,String productDescription,List<MultipartFile> imageFiles, String mainCategoryName, String  subMainCategoryName,String subCategoryName) throws IOException;

    List<ProductDto> getAllProducts();

    Map<String, Object> updateProduct(Long productId,String productName,String productDescription, List<MultipartFile> imageFiles, String mainCategoryName, String  subMainCategoryName,String subCategoryName) throws IOException;

    Boolean deleteProduct(Long productId);

    ProductDto getProductById(Long productId);
}
