package com.enigmacamp.loanapp.service.impl;

import com.enigmacamp.loanapp.model.entity.Role;
import com.enigmacamp.loanapp.repository.RoleRepository;
import com.enigmacamp.loanapp.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    @Override
    public Role getRole(Role.ERole role) {
        Optional<Role> optionalRole = roleRepository.findByRole(role);
        return optionalRole.orElseGet(() -> roleRepository.saveAndFlush(Role.builder()
                .role(role)
                .build()));
    }
}
