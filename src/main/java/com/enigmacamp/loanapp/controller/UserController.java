package com.enigmacamp.loanapp.controller;

import com.enigmacamp.loanapp.constant.APIUrl;
import com.enigmacamp.loanapp.model.dto.response.CommonResponse;
import com.enigmacamp.loanapp.model.dto.response.UserResponse;
import com.enigmacamp.loanapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(APIUrl.USER_API)
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<UserResponse>> getUserById(@PathVariable String id) {
        UserResponse userResponse = userService.getUserById(id);
        CommonResponse<UserResponse> commonResponse = CommonResponse.<UserResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Successfully retrieved user by id")
                .data(Optional.of(userResponse))
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }
}
