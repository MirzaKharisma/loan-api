package com.enigmacamp.loanapp.service.impl;

import com.enigmacamp.loanapp.model.authorize.AppUser;
import com.enigmacamp.loanapp.model.dto.request.AuthRequest;
import com.enigmacamp.loanapp.model.dto.request.CustomerRequest;
import com.enigmacamp.loanapp.model.dto.response.LoginResponse;
import com.enigmacamp.loanapp.model.dto.response.RegisterResponse;
import com.enigmacamp.loanapp.model.entity.Role;
import com.enigmacamp.loanapp.model.entity.User;
import com.enigmacamp.loanapp.repository.UserRepository;
import com.enigmacamp.loanapp.service.AuthService;
import com.enigmacamp.loanapp.service.CustomerService;
import com.enigmacamp.loanapp.service.RoleService;
import com.enigmacamp.loanapp.utils.exception.ResourceNotFoundException;
import com.enigmacamp.loanapp.utils.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final JwtUtil jwtUtil;
    private final CustomerService customerService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;

    @Override
    public RegisterResponse registerCustomer(AuthRequest<CustomerRequest> request) {
        Role role = roleService.getRole(Role.ERole.ROLE_CUSTOMER);
        Optional<User> user = userRepository.findByEmail(request.getEmail());
        User userResult = new User();
        if (user.isPresent()) {
            Set<Role> userRoles = user.get().getRoles();
            userRoles.add(role);
            user.get().setRoles(userRoles);
            userResult = userRepository.saveAndFlush(user.get());
        }else{
            Set<Role> newRoles = new HashSet<>();
            newRoles.add(role);
            User newUser = User.builder()
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .roles(newRoles)
                    .build();
            userResult = userRepository.saveAndFlush(newUser);
        }

        CustomerRequest customerRequest = request.getData().orElseThrow(() ->
                new ResourceNotFoundException("Customer not found!"));

        customerRequest.setUser(userResult);

        customerService.create(customerRequest);

        return RegisterResponse.builder()
                .email(userResult.getEmail())
                .roles(userResult.getRoles().stream().map(Role::getRole).toList())
                .build();
    }

    @Override
    public RegisterResponse registerAdmin(AuthRequest<String> request) {
        Role role = roleService.getRole(Role.ERole.ROLE_ADMIN);
        Optional<User> user = userRepository.findByEmail(request.getEmail());
        User userResult = new User();
        if (user.isPresent()) {
            Set<Role> userRoles = user.get().getRoles();
            userRoles.add(role);
            user.get().setRoles(userRoles);
            userResult = userRepository.saveAndFlush(user.get());
        }else {
            Set<Role> newRoles = new HashSet<>();
            newRoles.add(role);
            User newUser = User.builder()
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .roles(newRoles)
                    .build();
            userResult = userRepository.saveAndFlush(newUser);
        }

        return RegisterResponse.builder()
                .email(userResult.getEmail())
                .roles(userResult.getRoles().stream().map(Role::getRole).toList())
                .build();
    }

    @Override
    public RegisterResponse registerStaff(AuthRequest<String> request) {
        User userResult;
        Role role = roleService.getRole(Role.ERole.ROLE_STAFF);
        Optional<User> user = userRepository.findByEmail(request.getEmail());
        if (user.isPresent()) {
            Set<Role> userRoles = user.get().getRoles();
            userRoles.add(role);
            user.get().setRoles(userRoles);
            userResult = userRepository.saveAndFlush(user.get());
        }else {
            Set<Role> newRoles = new HashSet<>();
            newRoles.add(role);
            User newUser = User.builder()
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .roles(newRoles)
                    .build();
            userResult = userRepository.saveAndFlush(newUser);
        }

        System.out.println(userResult);

        return RegisterResponse.builder()
                .email(userResult.getEmail())
                .roles(userResult.getRoles().stream().map(Role::getRole).toList())
                .build();
    }

    @Override
    public LoginResponse login(AuthRequest<String> request) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        request.getEmail(), request.getPassword()
                ));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        AppUser appUser = (AppUser) authentication.getPrincipal();

        String token = jwtUtil.generateToken(appUser);
        return LoginResponse.builder()
                .email(appUser.getEmail())
                .roles(appUser.getRoles())
                .token(token)
                .build();
    }
}
