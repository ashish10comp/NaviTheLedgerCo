package com.application.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.application.model.entity.Loan;

public interface LoanRepository extends JpaRepository<Loan, Long> {

    @Query(" Select l from Loan l "
            + " where l.bankName = :bankName "
            + " and l.borrowerName = :borrowerName")
    List<Loan> findLoanByBankNameAndBorrowerName(@Param("bankName") String bankName, @Param("borrowerName") String borrowerName);
}