package com.enigmacamp.loanapp.repository;

import com.enigmacamp.loanapp.model.entity.LoanTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LoanTransactionRepository extends JpaRepository<LoanTransaction, String> {
    @Query("SELECT lt FROM LoanTransaction lt JOIN lt.loanTransactionDetails ltd WHERE ltd.id = :detailId")
    LoanTransaction findByTransactionDetailId(@Param("detailId") String detailId);
}
