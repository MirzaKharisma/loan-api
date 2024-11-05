package com.enigmacamp.loanapp.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoanTypeResponse {
    private String id;
    private String type;
    private Double maxLoan;
}
