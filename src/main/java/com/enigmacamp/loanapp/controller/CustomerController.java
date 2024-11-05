package com.enigmacamp.loanapp.controller;

import com.enigmacamp.loanapp.constant.APIUrl;
import com.enigmacamp.loanapp.model.dto.request.CustomerRequest;
import com.enigmacamp.loanapp.model.dto.response.CommonResponse;
import com.enigmacamp.loanapp.model.dto.response.CustomerProfilePictureResponse;
import com.enigmacamp.loanapp.model.dto.response.CustomerResponse;
import com.enigmacamp.loanapp.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(APIUrl.CUSTOMER_API)
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping
    public ResponseEntity<CommonResponse<List<CustomerResponse>>> getAllCustomer() {
        List<CustomerResponse> response = customerService.getAll();
        CommonResponse<List<CustomerResponse>> commonResponse = CommonResponse.<List<CustomerResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Successfully retrieved all customers")
                .data(Optional.of(response))
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<CustomerResponse>> getCustomerById(@PathVariable String id) {
        CustomerResponse customerResponse = customerService.getById(id);
        CommonResponse<CustomerResponse> commonResponse = CommonResponse.<CustomerResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Successfully retrieved customer by id")
                .data(Optional.of(customerResponse))
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }

    @PutMapping
    public ResponseEntity<CommonResponse<CustomerResponse>> updateCustomer(@RequestBody CustomerRequest customerRequest) {
        CustomerResponse customerResponse = customerService.update(customerRequest);
        CommonResponse<CustomerResponse> commonResponse = CommonResponse.<CustomerResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Successfully update customer")
                .data(Optional.of(customerResponse))
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse<?>> deleteCustomer(@PathVariable String id) {
        customerService.delete(id);
        CommonResponse<?> commonResponse = CommonResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Successfully delete customer")
                .data(Optional.empty())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }

    @PatchMapping("/{id}/upload/avatar")
    public ResponseEntity<CommonResponse<CustomerProfilePictureResponse>> uploadAvatar(@PathVariable String id, @RequestParam("avatar") MultipartFile file) {
        String fileDownloadUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(APIUrl.CUSTOMER_API)
                .path("/" +  id +"/avatar/")
                .path(id+ "_" + file.getOriginalFilename())
                .toUriString();
        CustomerProfilePictureResponse response = customerService.uploadProfilePicture(file,id, fileDownloadUrl);
        CommonResponse<CustomerProfilePictureResponse> commonResponse = CommonResponse.<CustomerProfilePictureResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Successfully upload avatar")
                .data(Optional.of(response))
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponse);
    }


    @GetMapping("/{id}/avatar/{fileName}")
    public ResponseEntity<byte[]> getAvatar(@PathVariable String fileName, @PathVariable String id) {
        byte[] image = customerService.downloadProfilePicture(fileName);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.IMAGE_PNG).body(image);
    }
}
