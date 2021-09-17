package com.application.service.implementation;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.application.model.dto.LoanDTO;
import com.application.model.entity.Loan;
import com.application.repository.LoanRepository;
import com.application.service.LoanService;

@Service
@Transactional
public class LoanServiceImpl implements LoanService {

    @Autowired
    private LoanRepository loanRepository;

    @Override
    public Loan create(LoanDTO loanDTO) throws Exception {
        Integer totalAmount = (loanDTO.getPrincipal() + getInterestAmount(loanDTO));
        Loan loan = new Loan();
        loan.setBankName(loanDTO.getBankName());
        loan.setBorrowerName(loanDTO.getBorrowerName());
        loan.setPrincipal(loanDTO.getPrincipal());
        loan.setYears(loanDTO.getYears());
        loan.setRate(loanDTO.getRate());
        loan.setTotalAmount(totalAmount);
        loan.setEmiAmount(getEMIAmount(totalAmount, loanDTO.getYears()));
        return loanRepository.save(loan);
    }

    @Override
    public Loan getLoanWithBankNameAndBorrowerName(LoanDTO loanDTO) {
        return loanRepository.findLoanByBankNameAndBorrowerName(loanDTO.getBankName(), loanDTO.getBorrowerName()).get(0);
    }

    private int getInterestAmount(LoanDTO loanDTO) {
        return ((int) Math.ceil(((double) (loanDTO.getPrincipal() * loanDTO.getYears() * loanDTO.getRate())) / 100.0));
    }

    private int getEMIAmount(Integer totalAmount, Integer years) {
        return ((int) Math.ceil(((double) totalAmount) / ((double) (12 * years))));
    }
}