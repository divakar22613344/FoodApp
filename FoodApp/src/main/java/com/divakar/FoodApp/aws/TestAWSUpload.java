package com.divakar.FoodApp.aws;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/upload")
public class TestAWSUpload {
    private final AWSS3Service awss3Service;


    @PostMapping
    public  ResponseEntity<String> upload(
        @RequestParam("file") MultipartFile file, 
        @RequestParam("keyName") String keyName){

        java.net.URL savedFile = awss3Service.uploadFile(keyName, file);
        return ResponseEntity.ok("SAVED SUCCESSFULLY: " + savedFile.toString());
        
    }
}
