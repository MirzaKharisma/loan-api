package com.enigmacamp.loanapp.service;

import com.enigmacamp.loanapp.model.entity.GuaranteePicture;
import org.springframework.web.multipart.MultipartFile;

public interface GuaranteePictureService {
    GuaranteePicture defaultPath();
    GuaranteePicture upload(MultipartFile file, String loanTrxlId, String path);
    byte[] download(String fileName);
}
