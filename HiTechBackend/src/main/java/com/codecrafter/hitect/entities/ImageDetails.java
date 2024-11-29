package com.codecrafter.hitect.entities;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ImageDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long imageDetailsId;

    public String imageName;

    @ManyToOne
    @JoinColumn(name = "productId")
    private Product product;
}
