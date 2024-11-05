package com.enigmacamp.loanapp.service.impl;

import com.enigmacamp.loanapp.model.entity.CustomerProfilePicture;
import com.enigmacamp.loanapp.repository.CustomerProfilePictureRepository;
import com.enigmacamp.loanapp.service.CustomerProfilePictureService;
import com.enigmacamp.loanapp.utils.exception.FileStorageException;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@Service
public class CustomerProfilePictureServiceImpl implements CustomerProfilePictureService {
    private final CustomerProfilePictureRepository customerProfilePictureRepository;
    private final Path rootLocation;

    @Autowired
    public CustomerProfilePictureServiceImpl(CustomerProfilePictureRepository customerProfilePictureRepository) {
        this.customerProfilePictureRepository = customerProfilePictureRepository;
        this.rootLocation = Path.of("assets/images/profile-pictures");
        try{
            Files.createDirectories(rootLocation);
        }catch (IOException e){
            throw new RuntimeException("Failed to initialize file storage service");
        }
    }

    @Override
    public CustomerProfilePicture defaultPath(){
        String fileName = "dummy-profile-picture.png";
        try{
            Path targetLocation =  this.rootLocation.resolve(fileName);
            CustomerProfilePicture customerProfilePicture = CustomerProfilePicture.builder()
                    .contentType("image/jpeg")
                    .size(Files.size(targetLocation))
                    .name(fileName)
                    .url(targetLocation.toString())
                    .build();
            return customerProfilePictureRepository.saveAndFlush(customerProfilePicture);
        }catch (IOException e){
            throw new RuntimeException("Failed to initialize file storage service");
        }
    }

    @Override
    public CustomerProfilePicture upload(MultipartFile file, String customerId, String url) {
        String fileName = Objects.requireNonNull(file.getOriginalFilename());
        fileName = customerId + "_" + fileName;
        try{
            Path targetLocation =  this.rootLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            CustomerProfilePicture customerProfilePicture = CustomerProfilePicture.builder()
                    .contentType(file.getContentType())
                    .size(file.getSize())
                    .name(fileName)
                    .url(url)
                    .build();
            return customerProfilePictureRepository.saveAndFlush(customerProfilePicture);
        }catch (IOException e){
            throw new RuntimeException("Failed to initialize file storage service");
        }
    }

    @Override
    public byte[] download(String fileName) {
        String filename = null;
        try {
            filename = "assets/images/profile-pictures/" + fileName;
            Path path = Paths.get(filename);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            Thumbnails.of(path.toFile())
                    .size(200, 200)
                    .outputFormat("png")
                    .toOutputStream(outputStream);

            return outputStream.toByteArray();
        } catch (IOException e) {
            throw new FileStorageException("Could not load file: " + filename + ": " + e);
        }
    }
}
