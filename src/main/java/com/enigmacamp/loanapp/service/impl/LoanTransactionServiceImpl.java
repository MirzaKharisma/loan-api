package com.enigmacamp.loanapp.service.impl;

import com.enigmacamp.loanapp.model.dto.request.ApprovalRequest;
import com.enigmacamp.loanapp.model.dto.request.LoanTransactionRequest;
import com.enigmacamp.loanapp.model.dto.response.LoanTransactionDetailResponse;
import com.enigmacamp.loanapp.model.dto.response.LoanTransactionResponse;
import com.enigmacamp.loanapp.model.entity.*;
import com.enigmacamp.loanapp.repository.LoanTransactionDetailsRepository;
import com.enigmacamp.loanapp.repository.LoanTransactionRepository;
import com.enigmacamp.loanapp.service.*;
import com.enigmacamp.loanapp.utils.exception.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LoanTransactionServiceImpl implements LoanTransactionService {
    private final GuaranteePictureService guaranteePictureService;
    private final LoanTransactionRepository loanTransactionRepository;
    private final LoanTransactionDetailsRepository loanTransactionDetailsRepository;
    private final LoanTypeService loanTypeService;
    private final InstalmentService instalmentService;
    private final CustomerService customerService;
    private final UserService userService;

    @Override
    public LoanTransactionResponse create(LoanTransactionRequest request) {
        LoanType loanType = loanTypeService.getByIdToTransaction(request.getLoanType().getId());
        InstalmentType instalmentType = instalmentService.getByIdToTransaction(request.getInstalmentType().getId());
        Customer customer = customerService.getByIdToTransaction(request.getCustomer().getId());
        Double nominal = request.getNominal();
        Long createdAt = System.currentTimeMillis();

        LoanTransaction loanTransaction = loanTransactionRepository.saveAndFlush(LoanTransaction.builder()
                .loanType(loanType)
                .instalmentType(instalmentType)
                .customer(customer)
                .nominal(nominal)
                .createdAt(createdAt)
                .build());
        return convertLoanTransaction(loanTransaction);
    }

    @Override
    public LoanTransactionResponse getById(String id) {
        return convertLoanTransaction(findByIdOrThrow(id));
    }

    @Override
    @Transactional
    public LoanTransactionResponse approve(ApprovalRequest approvalRequest, String adminId) {
        LoanTransaction transaction = findByIdOrThrow(approvalRequest.getLoanTransactionId());
        User user = userService.getByIdToTransaction(adminId);

        transaction.setApproveAt(System.currentTimeMillis());
        transaction.setApproveBy(user.getEmail());
        transaction.setApprovalStatus(LoanTransaction.EApprovalStatus.APPROVED);
        Double nominalCount = transaction.getNominal() + transaction.getNominal()*approvalRequest.getInterestRates()/100;
        List<LoanTransactionDetail> transactionDetails = transaction.getLoanTransactionDetails();
        transaction.setUpdatedAt(System.currentTimeMillis());

        LoanTransactionDetail transactionDetail = LoanTransactionDetail.builder()
                .transactionDate(System.currentTimeMillis())
                .loanTransaction(transaction)
                .nominal(nominalCount)
                .loanStatus(LoanTransactionDetail.ELoanStatus.PAID)
                .guaranteePicture(guaranteePictureService.defaultPath())
                .createdAt(System.currentTimeMillis())
                .updatedAt(System.currentTimeMillis())
                .build();

        transactionDetails.add(transactionDetail);
        loanTransactionDetailsRepository.save(transactionDetail);

        transaction.setLoanTransactionDetails(transactionDetails);
        return convertLoanTransaction(loanTransactionRepository.saveAndFlush(transaction));
    }

    @Override
    @Transactional
    public LoanTransactionResponse uploadGuaranteePicture(MultipartFile file, String transactionId, String path) {
        LoanTransaction transaction = loanTransactionRepository.findById(transactionId).orElseThrow(() -> new ResourceNotFoundException("Transaction not found"));
        GuaranteePicture picture = guaranteePictureService.upload(file, transactionId, path);
        transaction.getLoanTransactionDetails().forEach(transactionDetail -> {
            transactionDetail.setGuaranteePicture(picture);
            loanTransactionDetailsRepository.save(transactionDetail);
        });
        transaction.setUpdatedAt(System.currentTimeMillis());
        return convertLoanTransaction(loanTransactionRepository.saveAndFlush(transaction));
    }

    @Override
    public byte[] downloadGuaranteePicture(String fileName) {
        return guaranteePictureService.download(fileName);
    }

    private LoanTransaction findByIdOrThrow(String id) {
        return loanTransactionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Transaction has not been found"));
    }

    private LoanTransactionResponse convertLoanTransaction(LoanTransaction loanTransaction) {
        if (loanTransaction.getLoanTransactionDetails() == null) {
            return LoanTransactionResponse.builder()
                    .id(loanTransaction.getId())
                    .loanTypeId(loanTransaction.getLoanType().getId())
                    .instalmentTypeId(loanTransaction.getInstalmentType().getId())
                    .customerId(loanTransaction.getCustomer().getId())
                    .nominal(loanTransaction.getNominal())
                    .approveAt(loanTransaction.getApproveAt())
                    .approveBy(loanTransaction.getApproveBy())
                    .approvalStatus(loanTransaction.getApprovalStatus())
                    .createdAt(loanTransaction.getCreatedAt())
                    .updatedAt(loanTransaction.getUpdatedAt())
                    .build();
        }
        List<LoanTransactionDetailResponse> loanTransactionDetailResponseList = loanTransaction.getLoanTransactionDetails().stream()
                .map(this::convertLoanTransactionDetail).toList();

        return LoanTransactionResponse.builder()
                .id(loanTransaction.getId())
                .loanTypeId(loanTransaction.getLoanType().getId())
                .instalmentTypeId(loanTransaction.getInstalmentType().getId())
                .customerId(loanTransaction.getCustomer().getId())
                .nominal(loanTransaction.getNominal())
                .approveAt(loanTransaction.getApproveAt())
                .approveBy(loanTransaction.getApproveBy())
                .approvalStatus(loanTransaction.getApprovalStatus())
                .loanTransactionDetailResponses(loanTransactionDetailResponseList)
                .createdAt(loanTransaction.getCreatedAt())
                .updatedAt(loanTransaction.getUpdatedAt())
                .build();
    }

    private LoanTransactionDetailResponse convertLoanTransactionDetail(LoanTransactionDetail loanTransactionDetail) {
        return LoanTransactionDetailResponse.builder()
                .id(loanTransactionDetail.getId())
                .transactionDate(loanTransactionDetail.getTransactionDate())
                .nominal(loanTransactionDetail.getNominal())
                .guaranteePicture(loanTransactionDetail.getGuaranteePicture())
                .loanStatus(loanTransactionDetail.getLoanStatus())
                .createdAt(loanTransactionDetail.getCreatedAt())
                .updatedAt(loanTransactionDetail.getUpdatedAt())
                .build();
    }
}
