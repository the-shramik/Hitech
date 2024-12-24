package com.codecrafter.hitect.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    private String productName;

    private String productDescription;

    @Transient
    private String imageBase64;

    @OneToMany(mappedBy = "product")
    @JsonIgnore
    private List<ImageDetails> imageDetails;

    private String mainCategoryName;

    private String subMainCategoryName;

    private String subCategoryName;
}
