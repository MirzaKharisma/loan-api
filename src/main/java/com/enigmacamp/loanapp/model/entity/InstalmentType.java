package com.enigmacamp.loanapp.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "t_instalment_type")
public class InstalmentType {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Enumerated(EnumType.STRING)
    @Column(name = "instalment_type", nullable = false)
    private EInstalmentType instalmentType;

    public enum EInstalmentType {
        ONE_MONTH,
        THREE_MONTHS,
        SIXTH_MONTHS,
        NINE_MONTHS,
        TWELVE_MONTHS
    }
}
