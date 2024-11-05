package com.enigmacamp.loanapp.service;

import com.enigmacamp.loanapp.model.dto.request.ApprovalRequest;
import com.enigmacamp.loanapp.model.dto.request.LoanTransactionRequest;
import com.enigmacamp.loanapp.model.dto.response.LoanTransactionResponse;
import org.springframework.web.multipart.MultipartFile;

public interface LoanTransactionService {
    LoanTransactionResponse create(LoanTransactionRequest loanTransactionRequest);
    LoanTransactionResponse getById(String id);
    LoanTransactionResponse approve(ApprovalRequest approvalRequest, String adminId);
    LoanTransactionResponse uploadGuaranteePicture(MultipartFile file, String transactionDetailsId, String url);
    byte[] downloadGuaranteePicture(String fileName);
}
