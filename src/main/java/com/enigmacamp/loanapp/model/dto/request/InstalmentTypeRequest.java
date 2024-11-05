package com.enigmacamp.loanapp.model.dto.request;

import com.enigmacamp.loanapp.model.entity.InstalmentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InstalmentTypeRequest {
    private String id;
    private InstalmentType.EInstalmentType instalmentType;
}
