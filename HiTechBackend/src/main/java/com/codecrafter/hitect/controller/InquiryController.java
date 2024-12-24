package com.codecrafter.hitect.controller;

import com.codecrafter.hitect.entities.InquiryForm;
import com.codecrafter.hitect.services.IInquiryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
@CrossOrigin("*")
public class InquiryController {

    private final IInquiryService inquiryService;

    @PostMapping("/add-inquiry")
    public ResponseEntity<?> addInquiry(@RequestBody InquiryForm inquiryForm){
        try {
           return ResponseEntity.status(HttpStatus.CREATED).body(inquiryService.inquiryForm(inquiryForm));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/get-inquiries")
    public ResponseEntity<?> getAllInquiries(){
        return ResponseEntity.ok(inquiryService.getInquiryForms());
    }

    @DeleteMapping("/delete-inquiry")
    public ResponseEntity<?> deleteInquiry(@RequestParam Long inquiryId){
        if(inquiryService.deleteInquiry(inquiryId)){
            return ResponseEntity.status(HttpStatus.OK).body("Inquiry deleted successfully..!");
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Inquiry not deleted!");
        }
    }
}
