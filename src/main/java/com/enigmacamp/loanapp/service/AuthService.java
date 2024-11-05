package com.enigmacamp.loanapp.service;

import com.enigmacamp.loanapp.model.dto.request.AuthRequest;
import com.enigmacamp.loanapp.model.dto.request.CustomerRequest;
import com.enigmacamp.loanapp.model.dto.response.LoginResponse;
import com.enigmacamp.loanapp.model.dto.response.RegisterResponse;

public interface AuthService {
    RegisterResponse registerCustomer(AuthRequest<CustomerRequest> request);

    RegisterResponse registerAdmin(AuthRequest<String> request);

    RegisterResponse registerStaff(AuthRequest<String> request);

    LoginResponse login(AuthRequest<String> request);
}
