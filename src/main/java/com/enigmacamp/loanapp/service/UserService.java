package com.enigmacamp.loanapp.service;

import com.enigmacamp.loanapp.model.authorize.AppUser;
import com.enigmacamp.loanapp.model.dto.response.UserResponse;
import com.enigmacamp.loanapp.model.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    AppUser loadUserById(String id);
    UserResponse getUserById(String id);
    User getByIdToTransaction(String id);
}
