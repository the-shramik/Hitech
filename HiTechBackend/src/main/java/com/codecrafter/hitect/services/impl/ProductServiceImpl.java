package com.codecrafter.hitect.services.impl;


import com.codecrafter.hitect.entities.ImageDetails;
import com.codecrafter.hitect.entities.Product;
import com.codecrafter.hitect.entities.dtos.ProductDto;
import com.codecrafter.hitect.exception.ResourceNotFoundException;
import com.codecrafter.hitect.repository.IImageDetailsRepository;
import com.codecrafter.hitect.repository.IProductRepository;
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

    @Override
    public Map<String, Object> addProduct(String productName,String productDescription, List<MultipartFile> imageFiles, String mainCategoryName, String  subMainCategoryName,String subCategoryName) throws IOException {
        Product p = new Product();
        p.setProductName(productName);
        p.setProductDescription(productDescription);
        if(mainCategoryName!=null){
            p.setMainCategoryName(mainCategoryName.toUpperCase());
        }else {
            p.setMainCategoryName(null);
        }

        if(subMainCategoryName!=null){
            p.setSubMainCategoryName(subMainCategoryName.toUpperCase());
        }else{
            p.setSubMainCategoryName(null);
        }
        if(subCategoryName!=null){
            p.setSubCategoryName(subCategoryName.toUpperCase());
        }else{
            p.setSubCategoryName(null);
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
        response.put("mainCategory", savedProduct.getMainCategoryName());
        response.put("subMainCategory", savedProduct.getSubMainCategoryName());
        response.put("subCategory", savedProduct.getSubCategoryName());
        response.put("imageUrls", imageUrls);

        return response;
    }

    @Override
    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        List<ProductDto> productDtos = new ArrayList<>();
        for (Product product : products) {
            ProductDto productDto = new ProductDto();
            productDto.setProductId(product.getProductId());
            productDto.setProductName(product.getProductName());
            productDto.setMainCategoryName(product.getMainCategoryName());
            productDto.setSubMainCategoryName(product.getSubMainCategoryName());
            productDto.setSubCategoryName(product.getSubCategoryName());
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

    @Override
    public Map<String, Object> updateProduct(Long productId,String productName,String productDescription, List<MultipartFile> imageFiles, String mainCategoryName, String  subMainCategoryName,String subCategoryName) throws IOException {

        Product existedProduct=productRepository.findById(productId).orElseThrow(
                ()->new ResourceNotFoundException("Product not found with this product id")
        );


            existedProduct.setProductName(productName);
            existedProduct.setProductDescription(productDescription);
            existedProduct.setMainCategoryName(mainCategoryName.toUpperCase());
            existedProduct.setSubMainCategoryName(subMainCategoryName.toUpperCase());
            existedProduct.setSubCategoryName(subCategoryName.toUpperCase());

        Product savedProduct=productRepository.save(existedProduct);


        if(imageFiles!=null) {
            System.out.println("IF");

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
        }

        Map<String, Object> response = new HashMap<>();
        response.put("productId", savedProduct.getProductId());
        response.put("productName", savedProduct.getProductName());

        return response;
    }

    private File convertMultipartFileToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convFile)) {
            fos.write(file.getBytes());
        }
        return convFile;
    }


    @Override
    public Boolean deleteProduct(Long productId) {
        // Fetch the existing product
        Product existedProduct = productRepository.findById(productId).orElseThrow(
                () -> new ResourceNotFoundException("Product not found with this ID!")
        );

        // Fetch all associated images for the product
        List<ImageDetails> productImages = imageDetailsRepository.findAllByProduct(existedProduct);

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

    @Override
    public ProductDto getProductById(Long productId) {

        Product product=productRepository.findById(productId).orElseThrow(
                ()->new ResourceNotFoundException("Product is not present with this id")
        );

        ProductDto productDto=new ProductDto();
        productDto.setProductId(product.getProductId());
        productDto.setProductName(product.getProductName());
        productDto.setMainCategoryName(product.getMainCategoryName());
        productDto.setSubMainCategoryName(product.getSubMainCategoryName());
        productDto.setSubCategoryName(product.getSubCategoryName());
        productDto.setProductDescription(product.getProductDescription());
        List<String> imageUrls = new ArrayList<>();
        List<ImageDetails> imageDetails = imageDetailsRepository.findAllByProduct(product);
        for (ImageDetails imageDetail : imageDetails) {
            imageUrls.add(imageDetail.getImageName());
        }
        productDto.setImageUrls(imageUrls);
        return productDto;
    }

    @Override
    public List<ProductDto> getProductsByMainCategory(String mainCategoryName) {

        List<ProductDto> products=new ArrayList<>();
        productRepository.findAllByMainCategoryName(mainCategoryName).forEach(product -> {
            ProductDto productDto=new ProductDto();
            productDto.setProductId(product.getProductId());
            productDto.setProductName(product.getProductName());
            List<String> imageUrls = new ArrayList<>();
            List<ImageDetails> imageDetails = imageDetailsRepository.findAllByProduct(product);
            for (ImageDetails imageDetail : imageDetails) {
                imageUrls.add(imageDetail.getImageName());
            }
            productDto.setImageUrls(imageUrls);

            productDto.setMainCategoryName(product.getMainCategoryName());
            productDto.setSubMainCategoryName(product.getSubMainCategoryName());
            productDto.setSubCategoryName(product.getSubCategoryName());
            products.add(productDto);
        });

        return products;
    }

    @Override
    public List<ProductDto> getProductsBySubMainCategory(String subMainCategoryName) {
        List<ProductDto> products=new ArrayList<>();
        productRepository.findAllBySubMainCategoryName(subMainCategoryName).forEach(product -> {
            ProductDto productDto=new ProductDto();
            productDto.setProductId(product.getProductId());
            productDto.setProductName(product.getProductName());
            List<String> imageUrls = new ArrayList<>();
            List<ImageDetails> imageDetails = imageDetailsRepository.findAllByProduct(product);
            for (ImageDetails imageDetail : imageDetails) {
                imageUrls.add(imageDetail.getImageName());
            }
            productDto.setImageUrls(imageUrls);

            productDto.setMainCategoryName(product.getMainCategoryName());
            productDto.setSubMainCategoryName(product.getSubMainCategoryName());
            productDto.setSubCategoryName(product.getSubCategoryName());
            products.add(productDto);
        });

        return products;
    }

    @Override
    public List<ProductDto> getProductsBySubCategory(String subCategoryName) {
        List<ProductDto> products=new ArrayList<>();
        productRepository.findAllBySubCategoryName(subCategoryName).forEach(product -> {
            ProductDto productDto=new ProductDto();
            productDto.setProductId(product.getProductId());
            productDto.setProductName(product.getProductName());
            List<String> imageUrls = new ArrayList<>();
            List<ImageDetails> imageDetails = imageDetailsRepository.findAllByProduct(product);
            for (ImageDetails imageDetail : imageDetails) {
                imageUrls.add(imageDetail.getImageName());
            }
            productDto.setImageUrls(imageUrls);

            productDto.setMainCategoryName(product.getMainCategoryName());
            productDto.setSubMainCategoryName(product.getSubMainCategoryName());
            productDto.setSubCategoryName(product.getSubCategoryName());
            products.add(productDto);
        });

        return products;
    }

}
