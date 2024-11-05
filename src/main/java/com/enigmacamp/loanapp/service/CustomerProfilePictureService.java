package com.enigmacamp.loanapp.service;
import com.enigmacamp.loanapp.model.entity.CustomerProfilePicture;
import org.springframework.web.multipart.MultipartFile;

public interface CustomerProfilePictureService {
    CustomerProfilePicture defaultPath();
    CustomerProfilePicture upload(MultipartFile file, String customerId, String url);
    byte[] download(String fileName);
}
