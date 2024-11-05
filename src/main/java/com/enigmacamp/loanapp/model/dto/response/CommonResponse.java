package com.enigmacamp.loanapp.model.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommonResponse<T> {
    private Integer statusCode;
    private String message;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Optional<T> data;
}
