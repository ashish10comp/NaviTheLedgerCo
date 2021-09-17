package com.application.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.application.model.entity.EMI;

public interface EMIRepository extends JpaRepository<EMI, Long> {

    @Query(" Select e from EMI e "
            + " where e.loanId = :loanId "
            + " and e.emiNumber = :emiNumber")
    List<EMI> findEMIByLoanIdAndEMINumber(@Param("loanId") Long loanId, @Param("emiNumber") Integer emiNumber);
}