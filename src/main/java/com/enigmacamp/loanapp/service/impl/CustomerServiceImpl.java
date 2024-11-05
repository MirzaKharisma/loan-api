package com.enigmacamp.loanapp.service.impl;

import com.enigmacamp.loanapp.model.dto.request.CustomerRequest;
import com.enigmacamp.loanapp.model.dto.response.CustomerProfilePictureResponse;
import com.enigmacamp.loanapp.model.dto.response.CustomerResponse;
import com.enigmacamp.loanapp.model.entity.Customer;
import com.enigmacamp.loanapp.model.entity.CustomerProfilePicture;
import com.enigmacamp.loanapp.repository.CustomerRepository;
import com.enigmacamp.loanapp.service.CustomerProfilePictureService;
import com.enigmacamp.loanapp.service.CustomerService;
import com.enigmacamp.loanapp.utils.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerProfilePictureService customerProfilePictureService;

    @Override
    public List<CustomerResponse> getAll() {
        return customerRepository.findAll().stream().map(this::convertCustomerToCustomerResponse).collect(Collectors.toList());
    }

    @Override
    public CustomerResponse getById(String id) {
        Customer customer = findIdOrThrow(id);
        return convertCustomerToCustomerResponse(customer);
    }

    @Override
    public CustomerResponse create(CustomerRequest customerRequest) {
        CustomerProfilePicture customerProfilePicture = customerProfilePictureService.defaultPath();
        Customer customer = Customer.builder()
                .firstName(customerRequest.getFirstName())
                .lastName(customerRequest.getLastName())
                .phone(customerRequest.getPhoneNumber())
                .dateOfBirth(customerRequest.getDateOfBirth())
                .status(customerRequest.getStatus())
                .user(customerRequest.getUser())
                .profilePicture(customerProfilePicture)
                .build();

        return convertCustomerToCustomerResponse(customerRepository.saveAndFlush(customer));
    }

    @Override
    public CustomerResponse update(CustomerRequest customerRequest) {
        CustomerProfilePicture customerProfilePicture = customerProfilePictureService.defaultPath();
        Customer customer = findIdOrThrow(customerRequest.getId());
        customer.setFirstName(customerRequest.getFirstName());
        customer.setLastName(customerRequest.getLastName());
        customer.setPhone(customerRequest.getPhoneNumber());
        customer.setDateOfBirth(customerRequest.getDateOfBirth());
        customer.setStatus(customerRequest.getStatus());
        customer.setUser(customerRequest.getUser());
        customer.setProfilePicture(customerProfilePicture);
        return convertCustomerToCustomerResponse(customerRepository.saveAndFlush(customer));
    }

    @Override
    public Customer getByIdToTransaction(String id) {
        return findIdOrThrow(id);
    }

    @Override
    public CustomerProfilePictureResponse uploadProfilePicture(MultipartFile file, String id, String url) {
        Customer customer = findIdOrThrow(id);
        CustomerProfilePicture customerProfilePicture = customerProfilePictureService.upload(file,id, url);
        customer.setProfilePicture(customerProfilePicture);
        return CustomerProfilePictureResponse.builder()
                .name(customerProfilePicture.getName())
                .url(customerProfilePicture.getUrl())
                .build();
    }

    @Override
    public byte[] downloadProfilePicture(String fileName) {
        return customerProfilePictureService.download(fileName);
    }

    @Override
    public void delete(String id) {
        Customer customer = findIdOrThrow(id);
        customerRepository.delete(customer);
    }

    private Customer findIdOrThrow(String id) {
        return customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Customer has not been found"));
    }

    private CustomerResponse convertCustomerToCustomerResponse(Customer customer) {
        return CustomerResponse.builder()
                .id(customer.getId())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .dateOfBirth(customer.getDateOfBirth())
                .phoneNumber(customer.getPhone())
                .status(customer.getStatus())
                .build();
    }
}
