package com.enigmacamp.loanapp.controller;

import com.enigmacamp.loanapp.constant.APIUrl;
import com.enigmacamp.loanapp.model.dto.request.AuthRequest;
import com.enigmacamp.loanapp.model.dto.request.CustomerRequest;
import com.enigmacamp.loanapp.model.dto.response.CommonResponse;
import com.enigmacamp.loanapp.model.dto.response.LoginResponse;
import com.enigmacamp.loanapp.model.dto.response.RegisterResponse;
import com.enigmacamp.loanapp.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(APIUrl.AUTH_API)
public class AuthController {
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("signup/staff")
    public ResponseEntity<CommonResponse<RegisterResponse>> registerStaff(@RequestBody AuthRequest<String> request){
        RegisterResponse response = authService.registerStaff(request);
        CommonResponse<RegisterResponse> commonResponse = CommonResponse.<RegisterResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("New Staff Has Been Registered")
                .data(Optional.of(response))
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponse);
    }

    @PostMapping("signup/admin")
    public ResponseEntity<CommonResponse<RegisterResponse>> registerAdmin(@RequestBody AuthRequest<String> request){
        RegisterResponse response = authService.registerAdmin(request);
        CommonResponse<RegisterResponse> commonResponse = CommonResponse.<RegisterResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("New Admin Has Been Registered")
                .data(Optional.of(response))
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponse);
    }

    @PostMapping("signup/customer")
    public ResponseEntity<CommonResponse<RegisterResponse>> registerCustomer(@RequestBody AuthRequest<CustomerRequest> request){
        RegisterResponse response = authService.registerCustomer(request);
        CommonResponse<RegisterResponse> commonResponse = CommonResponse.<RegisterResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("New Customer Has Been Registered")
                .data(Optional.of(response))
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponse);
    }

    @PostMapping("login")
    public ResponseEntity<CommonResponse<LoginResponse>> login(@RequestBody AuthRequest<String> request){
        LoginResponse response = authService.login(request);
        CommonResponse<LoginResponse> commonResponse = CommonResponse.<LoginResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Login Successful")
                .data(Optional.of(response))
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }
}
