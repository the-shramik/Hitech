package com.codecrafter.hitect.services.impl;


import com.codecrafter.hitect.entities.ImageDetails;
import com.codecrafter.hitect.entities.Product;
import com.codecrafter.hitect.entities.SubCategory;
import com.codecrafter.hitect.entities.SubMainCategory;
import com.codecrafter.hitect.entities.dtos.ProductDto;
import com.codecrafter.hitect.exception.ResourceNotFoundException;
import com.codecrafter.hitect.repositories.IImageDetailsRepository;
import com.codecrafter.hitect.repositories.IProductRepository;
import com.codecrafter.hitect.services.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;
import software.amazon.awssdk.core.sync.RequestBody;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements IProductService {

    private final IProductRepository productRepository;
    private final IImageDetailsRepository imageDetailsRepository;

    private final S3Client s3Client;

    @CacheEvict(value = "products", allEntries = true)
    @Override
    public Map<String, Object> addProduct(String productName, List<MultipartFile> imageFiles, Long subMainCategoryId, Long subCategoryId) throws IOException {
        Product p = new Product();
        p.setProductName(productName);
        if(subCategoryId!=null){
            SubCategory subCategory=new SubCategory();
            subCategory.setSubCategoryId(subCategoryId);
            p.setSubCategory(subCategory);
        }
        if(subMainCategoryId!=null){
            SubMainCategory subMainCategory=new SubMainCategory();
            subMainCategory.setSubMainCategoryId(subMainCategoryId);
            p.setSubMainCategory(subMainCategory);
        }

        Product savedProduct = productRepository.save(p);


        List<String> imageUrls = new ArrayList<>();

        for (MultipartFile imageFile : imageFiles) {
            String imageName = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();

            File file = convertMultipartFileToFile(imageFile);
            String bucketName = "springboot-test-0076";

            // Upload file to S3
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(imageName)
                    .build();

            PutObjectResponse response = s3Client.putObject(putObjectRequest, RequestBody.fromFile(file));

            file.delete(); // Remove temporary file after upload

            // Get S3 URL
            String imageUrl = String.format("https://%s.s3.amazonaws.com/%s", bucketName, imageName);
            imageUrls.add(imageUrl);

            // Save image details
            ImageDetails imageDetails = new ImageDetails();
            imageDetails.setImageName(imageUrl);
            imageDetails.setProduct(savedProduct);
            imageDetailsRepository.save(imageDetails);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("productId", savedProduct.getProductId());
        response.put("productName", savedProduct.getProductName());
        response.put("imageUrls", imageUrls);

        return response;
    }

    @Cacheable(value = "products")
    @Override
    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        List<ProductDto> productDtos = new ArrayList<>();
        for (Product product : products) {
            ProductDto productDto = new ProductDto();
            productDto.setProductId(product.getProductId());
            productDto.setProductName(product.getProductName());
            List<String> imageUrls = new ArrayList<>();
            List<ImageDetails> imageDetails = imageDetailsRepository.findAllByProduct(product);
            for (ImageDetails imageDetail : imageDetails) {
                imageUrls.add(imageDetail.getImageName());
            }
            productDto.setImageUrls(imageUrls);
            productDtos.add(productDto);
        }
        return productDtos;
    }

    @CacheEvict(value = "products", allEntries = true)
    @Override
    public Map<String, Object> updateProduct(Long productId,String productName, List<MultipartFile> imageFiles, Long subMainCategoryId, Long subCategoryId) throws IOException {

        Product existedProduct=productRepository.findById(productId).orElseThrow(
                ()->new ResourceNotFoundException("Product not found with this product id")
        );

        if(productName!=null){
            existedProduct.setProductName(productName);
        }else{
            existedProduct.setProductName(existedProduct.getProductName());
        }

        if(subMainCategoryId!=null){
            SubMainCategory subMainCategory=new SubMainCategory();
            subMainCategory.setSubMainCategoryId(subMainCategoryId);
            existedProduct.setSubMainCategory(subMainCategory);
        }
        else{
            existedProduct.setSubMainCategory(existedProduct.getSubMainCategory());
        }

        if(subCategoryId!=null){
            SubCategory subCategory=new SubCategory();
            subCategory.setSubCategoryId(subCategoryId);
            existedProduct.setSubCategory(subCategory);
        }
        else{
            existedProduct.setSubCategory(existedProduct.getSubCategory());
        }

        Product savedProduct=productRepository.save(existedProduct);

        List<String> imageUrls = new ArrayList<>();

        for (MultipartFile imageFile : imageFiles) {
            String imageName = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();

            File file = convertMultipartFileToFile(imageFile);
            String bucketName = "springboot-test-0076";

            // Upload file to S3
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(imageName)
                    .build();

            PutObjectResponse response = s3Client.putObject(putObjectRequest, RequestBody.fromFile(file));

            file.delete(); // Remove temporary file after upload

            // Get S3 URL
            String imageUrl = String.format("https://%s.s3.amazonaws.com/%s", bucketName, imageName);
            imageUrls.add(imageUrl);

            // Save image details
            ImageDetails imageDetails = new ImageDetails();
            imageDetails.setImageName(imageUrl);
            imageDetails.setProduct(savedProduct);
            imageDetailsRepository.save(imageDetails);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("productId", savedProduct.getProductId());
        response.put("productName", savedProduct.getProductName());
        response.put("imageUrls", imageUrls);

        return response;
    }

    private File convertMultipartFileToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convFile)) {
            fos.write(file.getBytes());
        }
        return convFile;
    }


    @CacheEvict(value = "products", allEntries = true)
    @Override
    public Boolean deleteProduct(Long productId) {
        // Fetch the existing product
        Product existedProduct = productRepository.findById(productId).orElseThrow(
                () -> new ResourceNotFoundException("Product not found with this ID!")
        );

        // Fetch all associated images for the product
        List<ImageDetails> productImages = imageDetailsRepository.findByProduct(existedProduct);

        // Delete images from S3
        for (ImageDetails imageDetails : productImages) {
            // Extract image name from URL
            String imageName = imageDetails.getImageName().substring(
                    imageDetails.getImageName().lastIndexOf('/') + 1);

            // Delete the image from S3
            s3Client.deleteObject(DeleteObjectRequest.builder()
                    .bucket("springboot-test-0076")
                    .key(imageName)
                    .build());
        }

        // Delete images from the database
        imageDetailsRepository.deleteAll(productImages);

        // Delete the product from the database
        productRepository.deleteById(productId);

        return true;
    }

    @Cacheable(value = "products")
    @Override
    public List<ProductDto> getAllProductsBySubMainCategory(Long subMainCategoryId) {
        List<Product> products = productRepository.findBySubMainCategory(subMainCategoryId);
        List<ProductDto> productDtos = new ArrayList<>();
        for (Product product : products) {
            ProductDto productDto = new ProductDto();
            productDto.setProductId(product.getProductId());
            productDto.setProductName(product.getProductName());
            List<String> imageUrls = new ArrayList<>();
            List<ImageDetails> imageDetails = imageDetailsRepository.findAllByProduct(product);
            for (ImageDetails imageDetail : imageDetails) {
                imageUrls.add(imageDetail.getImageName());
            }
            productDto.setImageUrls(imageUrls);
            productDtos.add(productDto);
        }
        return productDtos;
    }

    @Cacheable(value = "products")
    @Override
    public List<ProductDto> getAllProductsBySubCategory(Long subCategoryId) {
        List<Product> products = productRepository.findBySubCategory(subCategoryId);
        List<ProductDto> productDtos = new ArrayList<>();
        for (Product product : products) {
            ProductDto productDto = new ProductDto();
            productDto.setProductId(product.getProductId());
            productDto.setProductName(product.getProductName());
            List<String> imageUrls = new ArrayList<>();
            List<ImageDetails> imageDetails = imageDetailsRepository.findAllByProduct(product);
            for (ImageDetails imageDetail : imageDetails) {
                imageUrls.add(imageDetail.getImageName());
            }
            productDto.setImageUrls(imageUrls);
            productDtos.add(productDto);
        }
        return productDtos;
    }

    @Cacheable(value = "products")
    @Override
    public List<ProductDto> getAllProductsByMainCategory(Long mainCategoryId) {
        List<Product> products = productRepository.findAllProductsByMainCategoryId(mainCategoryId);
        List<ProductDto> productDtos = new ArrayList<>();
        for (Product product : products) {
            ProductDto productDto = new ProductDto();
            productDto.setProductId(product.getProductId());
            productDto.setProductName(product.getProductName());
            List<String> imageUrls = new ArrayList<>();
            List<ImageDetails> imageDetails = imageDetailsRepository.findAllByProduct(product);
            for (ImageDetails imageDetail : imageDetails) {
                imageUrls.add(imageDetail.getImageName());
            }
            productDto.setImageUrls(imageUrls);
            productDtos.add(productDto);
        }
        return productDtos;
    }
}
