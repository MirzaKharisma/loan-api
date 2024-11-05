package com.enigmacamp.loanapp.service;

import com.enigmacamp.loanapp.model.dto.request.LoanTypeRequest;
import com.enigmacamp.loanapp.model.dto.response.LoanTypeResponse;
import com.enigmacamp.loanapp.model.entity.LoanType;

import java.util.List;

public interface LoanTypeService {
    LoanTypeResponse create(LoanTypeRequest instalmentTypeRequest);
    List<LoanTypeResponse> getAll();
    LoanTypeResponse getById(String id);
    LoanType getByIdToTransaction(String id);
    LoanTypeResponse update(LoanTypeRequest instalmentTypeRequest);
    void delete(String id);
}
