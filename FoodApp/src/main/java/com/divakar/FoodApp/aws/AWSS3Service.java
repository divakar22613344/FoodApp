package com.divakar.FoodApp.aws;

import java.net.URL;

import org.springframework.web.multipart.MultipartFile;

public interface AWSS3Service {
    URL uploadFile(String keyName, MultipartFile file);
    void deleteFile(String keyName);
}
