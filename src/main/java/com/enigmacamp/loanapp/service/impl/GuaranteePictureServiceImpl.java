package com.enigmacamp.loanapp.service.impl;

import com.enigmacamp.loanapp.model.entity.GuaranteePicture;
import com.enigmacamp.loanapp.repository.GuaranteePictureRepository;
import com.enigmacamp.loanapp.service.GuaranteePictureService;
import com.enigmacamp.loanapp.utils.exception.FileStorageException;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class GuaranteePictureServiceImpl implements GuaranteePictureService {
    private final GuaranteePictureRepository guaranteePictureRepository;
    private final Path rootLocation;

    @Autowired
    public GuaranteePictureServiceImpl(GuaranteePictureRepository guaranteePictureRepository) {
        this.guaranteePictureRepository = guaranteePictureRepository;
        this.rootLocation = Path.of("assets/images/guarantee-pictures");
        try{
            Files.createDirectories(rootLocation);
        }catch (IOException e){
            throw new RuntimeException("Failed to initialize file storage service");
        }
    }

    @Override
    public GuaranteePicture defaultPath(){
        String fileName = "dummy-guarantee-picture.png";
        try{
            Path targetLocation =  this.rootLocation.resolve(fileName);
            GuaranteePicture guaranteePicture = GuaranteePicture.builder()
                    .contentType("image/jpeg")
                    .size(Files.size(targetLocation))
                    .name(fileName)
                    .path(targetLocation.toString())
                    .build();
            return guaranteePictureRepository.saveAndFlush(guaranteePicture);
        }catch (IOException e){
            throw new RuntimeException("Failed to initialize file storage service");
        }
    }

    @Override
    public GuaranteePicture upload(MultipartFile file, String loanTransactionDetailId, String path) {
        String fileName = Objects.requireNonNull(file.getOriginalFilename());
        fileName = loanTransactionDetailId + "_" + fileName;
        try{
            Path targetLocation =  this.rootLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            GuaranteePicture guaranteePicture = GuaranteePicture.builder()
                    .contentType(file.getContentType())
                    .size(file.getSize())
                    .name(fileName)
                    .path(path)
                    .build();
            return guaranteePictureRepository.saveAndFlush(guaranteePicture);
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
