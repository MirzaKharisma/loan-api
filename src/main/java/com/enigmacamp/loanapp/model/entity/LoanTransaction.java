package com.enigmacamp.loanapp.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "trx_loan")
public class LoanTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "loan_type_id")
    private LoanType loanType;

    @ManyToOne
    @JoinColumn(name = "instalment_type_id")
    private InstalmentType instalmentType;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "loanTransaction")
    private List<LoanTransactionDetail> loanTransactionDetails;

    @Column(nullable = false)
    private Double nominal;

    @Column(name="approve_at")
    private Long approveAt;

    @Column(name="approve_by")
    private String approveBy;

    @Enumerated(EnumType.STRING)
    @Column(name = "approval_status")
    private EApprovalStatus approvalStatus;

    @Column(name = "created_at")
    private Long createdAt;

    @Column(name = "updated_at")
    private Long updatedAt;

    public enum EApprovalStatus{
        APPROVED,
        REJECTED
    }
}
