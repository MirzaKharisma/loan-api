package com.enigmacamp.loanapp.service;

import com.enigmacamp.loanapp.model.dto.request.CustomerRequest;
import com.enigmacamp.loanapp.model.dto.response.CustomerProfilePictureResponse;
import com.enigmacamp.loanapp.model.dto.response.CustomerResponse;
import com.enigmacamp.loanapp.model.entity.Customer;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CustomerService {
    List<CustomerResponse> getAll();
    CustomerResponse getById(String id);
    CustomerResponse create(CustomerRequest customerRequest);
    CustomerResponse update(CustomerRequest customerRequest);
    Customer getByIdToTransaction(String id);
    CustomerProfilePictureResponse uploadProfilePicture(MultipartFile file, String id, String url);
    byte[] downloadProfilePicture(String fileName);
    void delete(String id);
}
