package com.codecrafter.hitect.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "subcategories")
public class SubCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long subCategoryId;
    private String subCategoryName;
    private LocalDate subCategoryAddedDate;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "subMainCategoryId")
    private SubMainCategory subMainCategory;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "subCategory", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Product> products;
}
