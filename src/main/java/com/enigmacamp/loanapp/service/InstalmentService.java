package com.enigmacamp.loanapp.service;

import com.enigmacamp.loanapp.model.dto.request.InstalmentTypeRequest;
import com.enigmacamp.loanapp.model.dto.response.InstalmentTypeResponse;
import com.enigmacamp.loanapp.model.entity.InstalmentType;

import java.util.List;

public interface InstalmentService {
    InstalmentTypeResponse create(InstalmentTypeRequest instalmentTypeRequest);
    List<InstalmentTypeResponse> getAll();
    InstalmentTypeResponse getById(String id);
    InstalmentType getByIdToTransaction(String id);
    InstalmentTypeResponse update(InstalmentTypeRequest instalmentTypeRequest);
    void delete(String id);
}
