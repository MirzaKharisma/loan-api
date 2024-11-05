package com.enigmacamp.loanapp.model.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthRequest<T>{
    private String email;
    private String password;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Optional<T> data;
}
