package com.enigmacamp.loanapp.controller;

import com.enigmacamp.loanapp.constant.APIUrl;
import com.enigmacamp.loanapp.model.dto.request.InstalmentTypeRequest;
import com.enigmacamp.loanapp.model.dto.response.CommonResponse;
import com.enigmacamp.loanapp.model.dto.response.InstalmentTypeResponse;
import com.enigmacamp.loanapp.service.InstalmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(APIUrl.INSTALMENT_TYPE_API)
@RequiredArgsConstructor
public class InstalmentTypeController {
    
    private final InstalmentService instalmentTypeService;
    
    @PostMapping
    public ResponseEntity<CommonResponse<InstalmentTypeResponse>> create(@RequestBody InstalmentTypeRequest instalmentType) {
        InstalmentTypeResponse instalmentTypeResponse = instalmentTypeService.create(instalmentType);
        CommonResponse<InstalmentTypeResponse> commonResponse = CommonResponse.<InstalmentTypeResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Instalment type created successfully")
                .data(Optional.of(instalmentTypeResponse))
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponse);
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<InstalmentTypeResponse>>> getAllInstalmentType() {
        List<InstalmentTypeResponse> response = instalmentTypeService.getAll();
        CommonResponse<List<InstalmentTypeResponse>> commonResponse = CommonResponse.<List<InstalmentTypeResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Successfully retrieved all instalmentTypes")
                .data(Optional.of(response))
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<InstalmentTypeResponse>> getInstalmentTypeById(@PathVariable String id) {
        InstalmentTypeResponse instalmentTypeResponse = instalmentTypeService.getById(id);
        CommonResponse<InstalmentTypeResponse> commonResponse = CommonResponse.<InstalmentTypeResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Successfully retrieved instalmentType by id")
                .data(Optional.of(instalmentTypeResponse))
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }

    @PutMapping
    public ResponseEntity<CommonResponse<InstalmentTypeResponse>> updateInstalmentType(@RequestBody InstalmentTypeRequest instalmentTypeRequest) {
        InstalmentTypeResponse instalmentTypeResponse = instalmentTypeService.update(instalmentTypeRequest);
        CommonResponse<InstalmentTypeResponse> commonResponse = CommonResponse.<InstalmentTypeResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Successfully update instalmentType")
                .data(Optional.of(instalmentTypeResponse))
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse<?>> deleteInstalmentType(@PathVariable String id) {
        instalmentTypeService.delete(id);
        CommonResponse<?> commonResponse = CommonResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Successfully delete instalmentType")
                .data(Optional.empty())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }
}
