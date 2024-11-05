package com.enigmacamp.loanapp.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "trx_loan_detail")
public class LoanTransactionDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "transactio_date", nullable = false)
    private Long transactionDate;

    @Column(nullable = false)
    private Double nominal;

    @ManyToOne
    @JoinColumn(name = "trx_loan_id")
    @JsonIgnore
    private LoanTransaction loanTransaction;

    @ManyToOne
    @JoinColumn(name = "guarantee_picture_id")
    private GuaranteePicture guaranteePicture;

    @Enumerated(EnumType.STRING)
    @Column(name = "loan_status", nullable = false)
    private ELoanStatus loanStatus;

    @Column(name = "created_at", nullable = false)
    private Long createdAt;

    @Column(name = "updated_at", nullable = false)
    private Long updatedAt;

    public enum ELoanStatus {
        PAID,
        UNPAID
    }
}
