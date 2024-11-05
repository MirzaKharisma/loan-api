package com.enigmacamp.loanapp.controller;

import com.enigmacamp.loanapp.constant.APIUrl;
import com.enigmacamp.loanapp.model.dto.request.LoanTypeRequest;
import com.enigmacamp.loanapp.model.dto.response.CommonResponse;
import com.enigmacamp.loanapp.model.dto.response.LoanTypeResponse;
import com.enigmacamp.loanapp.service.LoanTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(APIUrl.LOAN_TYPE_API)
@RequiredArgsConstructor
public class LoanTypeController {
    private final LoanTypeService loanTypeService;

    @PostMapping
    public ResponseEntity<CommonResponse<LoanTypeResponse>> create(@RequestBody LoanTypeRequest loanType) {
        LoanTypeResponse loanTypeResponse = loanTypeService.create(loanType);
        CommonResponse<LoanTypeResponse> commonResponse = CommonResponse.<LoanTypeResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Loan type created successfully")
                .data(Optional.of(loanTypeResponse))
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponse);
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<LoanTypeResponse>>> getAllLoanType() {
        List<LoanTypeResponse> response = loanTypeService.getAll();
        CommonResponse<List<LoanTypeResponse>> commonResponse = CommonResponse.<List<LoanTypeResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Successfully retrieved all loan types")
                .data(Optional.of(response))
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<LoanTypeResponse>> getLoanTypeById(@PathVariable String id) {
        LoanTypeResponse loanTypeResponse = loanTypeService.getById(id);
        CommonResponse<LoanTypeResponse> commonResponse = CommonResponse.<LoanTypeResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Successfully retrieved loan type by id")
                .data(Optional.of(loanTypeResponse))
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }

    @PutMapping
    public ResponseEntity<CommonResponse<LoanTypeResponse>> updateLoanType(@RequestBody LoanTypeRequest loanTypeRequest) {
        LoanTypeResponse loanTypeResponse = loanTypeService.update(loanTypeRequest);
        CommonResponse<LoanTypeResponse> commonResponse = CommonResponse.<LoanTypeResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Successfully update loan type")
                .data(Optional.of(loanTypeResponse))
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse<?>> deleteLoanType(@PathVariable String id) {
        loanTypeService.delete(id);
        CommonResponse<?> commonResponse = CommonResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Successfully delete loan type")
                .data(Optional.empty())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }
}
