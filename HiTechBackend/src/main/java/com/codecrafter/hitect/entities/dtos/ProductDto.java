package com.codecrafter.hitect.entities.dtos;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private Long productId;
    private String productName;
    private String mainCategoryName;
    private String subMainCategoryName;
    private String subCategoryName;
    private String productDescription;
    private List<String> imageUrls;
}
