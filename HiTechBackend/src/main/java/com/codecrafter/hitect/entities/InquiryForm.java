package com.codecrafter.hitect.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.RestController;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "inquiry_form")
public class InquiryForm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inquiryFormId;
    private String name;
    private String contact;
    private String email;
    private String address;

//    @Lob
//    private String inquiry;
}
