package com.application.service.implementation;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.application.model.dto.BalanceDTO;
import com.application.model.dto.LoanDTO;
import com.application.model.entity.EMI;
import com.application.model.entity.Loan;
import com.application.repository.EMIRepository;
import com.application.service.BalanceService;
import com.application.service.LoanService;

@Service
@Transactional
public class BalanceServiceImpl implements BalanceService {

    @Autowired
    private LoanService loanService;

    @Autowired
    private EMIRepository emiRepository;

    @Override
    public BalanceDTO getBalanceEMI(BalanceDTO balanceDTO) throws Exception {
        LoanDTO loanDTO = new LoanDTO();
        loanDTO.setBankName(balanceDTO.getBankName());
        loanDTO.setBorrowerName(balanceDTO.getBorrowerName());
        Loan loan = loanService.getLoanWithBankNameAndBorrowerName(loanDTO);
        EMI emi = emiRepository.findEMIByLoanIdAndEMINumber(loan.getLoanId(), balanceDTO.getEmiNumber()).get(0);
        balanceDTO.setNumberOfEMISLeft(emi.getNumberOfEMISLeft());
        balanceDTO.setAmountPaid(emi.getAmountPaid());
        return balanceDTO;
    }
}