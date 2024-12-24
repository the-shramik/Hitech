package com.codecrafter.hitect.controller;

import com.codecrafter.hitect.services.IResetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reset")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ResetController {

    private final IResetService resetService;

    @DeleteMapping("/reset-all-data")
    public ResponseEntity<?> resetAllData(){
        try {
            return ResponseEntity.status(HttpStatus.OK).body("Deleted everything!");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Nothing is deleted!");
        }
    }
}
