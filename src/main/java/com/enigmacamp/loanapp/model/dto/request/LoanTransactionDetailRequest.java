package com.enigmacamp.loanapp.model.dto.request;

import com.enigmacamp.loanapp.model.entity.LoanTransactionDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoanTransactionDetailRequest {
    private String id;
    private Long transactionDate;
    private Double nominal;
    private LoanTransactionDetail.ELoanStatus loanStatus;
}
