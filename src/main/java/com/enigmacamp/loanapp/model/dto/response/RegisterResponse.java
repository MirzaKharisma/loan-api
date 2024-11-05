package com.enigmacamp.loanapp.model.dto.response;

import com.enigmacamp.loanapp.model.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterResponse {
    private String email;
    private List<Role.ERole> roles;
}
