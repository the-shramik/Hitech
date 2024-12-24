package com.codecrafter.hitect.controller;

import com.codecrafter.hitect.entities.SpecialOffer;
import com.codecrafter.hitect.services.ISpecialOfferService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/special-offer")
@CrossOrigin("*")
public class SpecialOfferController {

    private final ISpecialOfferService specialOfferService;

    @PostMapping("/add-special-offer")
    public ResponseEntity<?> addSpecialOffer(@RequestBody SpecialOffer specialOffer){

        System.out.println(specialOffer.getSpecialOffer());
        return ResponseEntity.ok(specialOfferService.addSpecialOffer(specialOffer));
    }

    @GetMapping("/get-all-special-offer")
    public ResponseEntity<?> getAllSpecialOffer(){
        return ResponseEntity.ok(specialOfferService.getAllSpecialOffers());
    }

    @PutMapping("/update-special-offer")
    public ResponseEntity<?> updateSpecialOffer(@RequestBody SpecialOffer specialOffer){
        boolean isUpdated= specialOfferService.updateSpecialOffer(specialOffer);

        if (isUpdated){
            return ResponseEntity.status(HttpStatus.OK).body("Special updated!");
        }
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Special not updated!");
        }

    }

    @DeleteMapping("/delete-special-offer")
    public ResponseEntity<?> deleteSpecialOffer(@RequestParam Long specialOfferId){
        boolean isDeleted= specialOfferService.deleteSpecialOffer(specialOfferId);

        if (isDeleted){
            return ResponseEntity.status(HttpStatus.OK).body("SpecialOffer deleted!");
        }
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("SpecialOffer not deleted!");
        }
    }
}