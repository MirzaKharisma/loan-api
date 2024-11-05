package com.enigmacamp.loanapp.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApprovalRequest {
    private String loanTransactionId;
    private Integer interestRates;
}
