package com.enigmacamp.loanapp.service.impl;

import com.enigmacamp.loanapp.model.dto.request.InstalmentTypeRequest;
import com.enigmacamp.loanapp.model.dto.response.InstalmentTypeResponse;
import com.enigmacamp.loanapp.model.entity.InstalmentType;
import com.enigmacamp.loanapp.repository.InstalmentTypeRepository;
import com.enigmacamp.loanapp.service.InstalmentService;
import com.enigmacamp.loanapp.utils.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InstalmentServiceImpl implements InstalmentService {
    private final InstalmentTypeRepository instalmentTypeRepository;

    @Override
    public List<InstalmentTypeResponse> getAll() {
        return instalmentTypeRepository.findAll().stream().map(this::convertInstalmentTypeToInstalmentTypeResponse).collect(Collectors.toList());
    }

    @Override
    public InstalmentTypeResponse getById(String id) {
        InstalmentType instalmentType = findIdOrThrow(id);
        return convertInstalmentTypeToInstalmentTypeResponse(instalmentType);
    }

    @Override
    public InstalmentType getByIdToTransaction(String id) {
        return findIdOrThrow(id);
    }

    @Override
    public InstalmentTypeResponse create(InstalmentTypeRequest instalmentTypeRequest) {
        InstalmentType instalmentType = InstalmentType.builder()
                .instalmentType(instalmentTypeRequest.getInstalmentType())
                .build();

        return convertInstalmentTypeToInstalmentTypeResponse(instalmentTypeRepository.saveAndFlush(instalmentType));
    }

    @Override
    public InstalmentTypeResponse update(InstalmentTypeRequest instalmentTypeRequest) {
        InstalmentType instalmentType = findIdOrThrow(instalmentTypeRequest.getId());
        instalmentType.setInstalmentType(instalmentTypeRequest.getInstalmentType());
        return convertInstalmentTypeToInstalmentTypeResponse(instalmentTypeRepository.saveAndFlush(instalmentType));
    }

    @Override
    public void delete(String id) {
        InstalmentType instalmentType = findIdOrThrow(id);
        instalmentTypeRepository.delete(instalmentType);
    }

    private InstalmentType findIdOrThrow(String id) {
        return instalmentTypeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("InstalmentType has not been found"));
    }

    private InstalmentTypeResponse convertInstalmentTypeToInstalmentTypeResponse(InstalmentType instalmentType) {
        return InstalmentTypeResponse.builder()
                .id(instalmentType.getId())
                .instalmentType(instalmentType.getInstalmentType())
                .build();
    }
}
