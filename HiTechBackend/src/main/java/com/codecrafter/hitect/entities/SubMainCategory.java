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
@Table(name = "submaincategories")
public class SubMainCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long subMainCategoryId;
    private String subMainCategoryName;
    private LocalDate subMainCategoryAddedDate;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "mainCategoryId")
    private MainCategory mainCategory;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "subMainCategory", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Product> products;


}
