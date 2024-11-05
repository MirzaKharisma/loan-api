package com.enigmacamp.loanapp.model.dto.response;

import com.enigmacamp.loanapp.model.entity.LoanTransaction;
import com.enigmacamp.loanapp.model.entity.LoanTransactionDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoanTransactionResponse {
    private String id;
    private String loanTypeId;
    private String instalmentTypeId;
    private String customerId;
    private Double nominal;
    private Long approveAt;
    private String approveBy;
    private LoanTransaction.EApprovalStatus approvalStatus;
    private List<LoanTransactionDetailResponse> loanTransactionDetailResponses;
    private Long createdAt;
    private Long updatedAt;
}
