package com.enigmacamp.loanapp.model.dto.request;
import com.enigmacamp.loanapp.model.entity.LoanTransactionDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoanTransactionRequest {
    private LoanTypeRequest loanType;
    private InstalmentTypeRequest instalmentType;
    private CustomerRequest customer;
    private Double nominal;
    private List<LoanTransactionDetail> loanTransactionDetails;
}
