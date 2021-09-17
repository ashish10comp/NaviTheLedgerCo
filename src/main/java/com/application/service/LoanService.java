package com.application.service;

import com.application.model.dto.LoanDTO;
import com.application.model.entity.Loan;

public interface LoanService {

    Loan create(LoanDTO loanDTO) throws Exception;

    Loan getLoanWithBankNameAndBorrowerName(LoanDTO loanDTO);
}