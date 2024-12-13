package com.codecrafter.hitect.controllers;

import com.codecrafter.hitect.entities.InquiryForm;
import com.codecrafter.hitect.services.IInquiryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/inquiry")
@CrossOrigin("*")
public class InquiryController {
    private final IInquiryService inquiryService;

    @PostMapping("/add-inquiry")
    public ResponseEntity<?> addInquiryForm(@RequestBody InquiryForm inquiryForm) {
        return ResponseEntity.ok(inquiryService.inquiryForm(inquiryForm));
    }
    @GetMapping("/get-inquiries")
    public ResponseEntity<?> getInquiryForms() {
        return ResponseEntity.ok(inquiryService.getInquiryForms());
    }

    @DeleteMapping("/delete-inquiry")
    public ResponseEntity<?> deleteInquiry(@RequestParam Long inquiryId){

        System.out.println(inquiryId);
        System.out.println("inquiry delete api working");
        boolean isDeleted=false;

        isDeleted= inquiryService.deleteInquiry(inquiryId);

        if(isDeleted){
            return ResponseEntity.status(HttpStatus.OK).body("Inquiry deleted!");
        }
        else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Inquiry not deleted please check inquiry id");
        }
    }
}
