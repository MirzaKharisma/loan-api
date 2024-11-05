package com.enigmacamp.loanapp.service.impl;

import com.enigmacamp.loanapp.model.authorize.AppUser;
import com.enigmacamp.loanapp.model.dto.response.UserResponse;
import com.enigmacamp.loanapp.model.entity.Role;
import com.enigmacamp.loanapp.model.entity.User;
import com.enigmacamp.loanapp.repository.UserRepository;
import com.enigmacamp.loanapp.service.UserService;
import com.enigmacamp.loanapp.utils.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public AppUser loadUserById(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid credential user"));
        return AppUser.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRoles().stream().map(Role::getRole).collect(Collectors.toList()))
                .build();
    }

    @Override
    public UserResponse getUserById(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid credential user"));
        return UserResponse.builder()
                .email(user.getEmail())
                .roles(user.getRoles())
                .build();
    }

    @Override
    public User getByIdToTransaction(String id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User has not been found"));
    }

    @Override
    public AppUser loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid credential user"));
        return AppUser.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRoles().stream().map(Role::getRole).collect(Collectors.toList()))
                .build();
    }
}
