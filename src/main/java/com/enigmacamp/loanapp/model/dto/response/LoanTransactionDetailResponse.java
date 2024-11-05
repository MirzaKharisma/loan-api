package com.enigmacamp.loanapp.model.dto.response;

import com.enigmacamp.loanapp.model.entity.GuaranteePicture;
import com.enigmacamp.loanapp.model.entity.LoanTransactionDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoanTransactionDetailResponse {
    private String id;
    private Long transactionDate;
    private Double nominal;
    private LoanTransactionDetail.ELoanStatus loanStatus;
    private Long createdAt;
    private Long updatedAt;
    private GuaranteePicture guaranteePicture;
}
