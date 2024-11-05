package com.enigmacamp.loanapp.controller;

import com.enigmacamp.loanapp.constant.APIUrl;
import com.enigmacamp.loanapp.model.dto.request.ApprovalRequest;
import com.enigmacamp.loanapp.model.dto.request.LoanTransactionRequest;
import com.enigmacamp.loanapp.model.dto.response.CommonResponse;
import com.enigmacamp.loanapp.model.dto.response.CustomerProfilePictureResponse;
import com.enigmacamp.loanapp.model.dto.response.LoanTransactionResponse;
import com.enigmacamp.loanapp.model.entity.LoanTransactionDetail;
import com.enigmacamp.loanapp.service.LoanTransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Optional;

@RestController
@RequestMapping(APIUrl.TRANSACTION_API)
@RequiredArgsConstructor
public class LoanTransactionController {

    private final LoanTransactionService loanTransactionService;

    @PostMapping
    public ResponseEntity<CommonResponse<LoanTransactionResponse>> createLoanTransaction(@RequestBody LoanTransactionRequest request) {
        LoanTransactionResponse response = loanTransactionService.create(request);
        CommonResponse<LoanTransactionResponse> commonResponse = CommonResponse.<LoanTransactionResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Transaction Created")
                .data(Optional.of(response))
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<LoanTransactionResponse>> getLoanTransaction(@PathVariable String id) {
        LoanTransactionResponse response = loanTransactionService.getById(id);
        CommonResponse<LoanTransactionResponse> commonResponse = CommonResponse.<LoanTransactionResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Transaction received successfully")
                .data(Optional.of(response))
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponse);
    }

    @PutMapping("/{adminId}/approve")
    public ResponseEntity<CommonResponse<LoanTransactionResponse>> approveLoanTransaction(@PathVariable String adminId, @RequestBody ApprovalRequest request) {
        LoanTransactionResponse response = loanTransactionService.approve(request, adminId);
        CommonResponse<LoanTransactionResponse> commonResponse = CommonResponse.<LoanTransactionResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Transaction approved successfully")
                .data(Optional.of(response))
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponse);
    }

    @PutMapping("/{trxId}/pay")
    public ResponseEntity<CommonResponse<LoanTransactionResponse>> pay(@PathVariable String trxId, @RequestParam("pay") MultipartFile file) {
        String fileDownloadUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(APIUrl.CUSTOMER_API)
                .path("/" +  trxId +"/pay/")
                .path(trxId+ "_" + file.getOriginalFilename())
                .toUriString();
        LoanTransactionResponse response = loanTransactionService.uploadGuaranteePicture(file, trxId, fileDownloadUrl);
        CommonResponse<LoanTransactionResponse> commonResponse = CommonResponse.<LoanTransactionResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Upload pay successfully")
                .data(Optional.of(response))
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponse);
    }

    @GetMapping("/{trxId}/pay/{fileName}")
    public ResponseEntity<byte[]> getAvatar(@PathVariable String fileName) {
        byte[] image = loanTransactionService.downloadGuaranteePicture(fileName);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.IMAGE_PNG).body(image);
    }
}
