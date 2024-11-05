package com.enigmacamp.loanapp.service.impl;

import com.enigmacamp.loanapp.model.dto.request.LoanTypeRequest;
import com.enigmacamp.loanapp.model.dto.response.LoanTypeResponse;
import com.enigmacamp.loanapp.model.entity.LoanType;
import com.enigmacamp.loanapp.repository.LoanTypeRepository;
import com.enigmacamp.loanapp.service.LoanTypeService;
import com.enigmacamp.loanapp.utils.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LoanTypeServiceImpl implements LoanTypeService {
    private final LoanTypeRepository loanTypeRepository;

    @Override
    public List<LoanTypeResponse> getAll() {
        return loanTypeRepository.findAll().stream().map(this::convertLoanTypeToLoanTypeResponse).collect(Collectors.toList());
    }

    @Override
    public LoanTypeResponse getById(String id) {
        LoanType loanType = findIdOrThrow(id);
        return convertLoanTypeToLoanTypeResponse(loanType);
    }

    @Override
    public LoanType getByIdToTransaction(String id) {
        return findIdOrThrow(id);
    }

    @Override
    public LoanTypeResponse create(LoanTypeRequest loanTypeRequest) {
        LoanType loanType = LoanType.builder()
                .type(loanTypeRequest.getType())
                .maxLoan(loanTypeRequest.getMaxLoan())
                .build();

        return convertLoanTypeToLoanTypeResponse(loanTypeRepository.saveAndFlush(loanType));
    }

    @Override
    public LoanTypeResponse update(LoanTypeRequest loanTypeRequest) {
        LoanType loanType = findIdOrThrow(loanTypeRequest.getId());
        loanType.setType(loanTypeRequest.getType());
        loanType.setMaxLoan(loanTypeRequest.getMaxLoan());
        return convertLoanTypeToLoanTypeResponse(loanTypeRepository.saveAndFlush(loanType));
    }

    @Override
    public void delete(String id) {
        LoanType loanType = findIdOrThrow(id);
        loanTypeRepository.delete(loanType);
    }

    private LoanType findIdOrThrow(String id) {
        return loanTypeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("LoanType has not been found"));
    }

    private LoanTypeResponse convertLoanTypeToLoanTypeResponse(LoanType loanType) {
        return LoanTypeResponse.builder()
                .id(loanType.getId())
                .type(loanType.getType())
                .maxLoan(loanType.getMaxLoan())
                .build();
    }
}
