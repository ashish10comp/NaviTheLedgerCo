package com.application.service.implementation;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.application.model.dto.LoanDTO;
import com.application.model.dto.PaymentDTO;
import com.application.model.entity.EMI;
import com.application.model.entity.Loan;
import com.application.repository.EMIRepository;
import com.application.service.LoanService;
import com.application.service.PaymentService;

@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private LoanService loanService;

    @Autowired
    private EMIRepository emiRepository;

    @Override
    public EMI payEMI(PaymentDTO paymentDTO) throws Exception {
        LoanDTO loanDTO = new LoanDTO();
        loanDTO.setBankName(paymentDTO.getBankName());
        loanDTO.setBorrowerName(paymentDTO.getBorrowerName());
        Loan loan = loanService.getLoanWithBankNameAndBorrowerName(loanDTO);
        Integer amountPaidSoFar = getAmountPaidSoFar(loan.getLoanId(), (paymentDTO.getEmiNumber() - 1));
        EMI nextEMI = new EMI();
        nextEMI.setLoanId(loan.getLoanId());
        nextEMI.setEmiNumber(paymentDTO.getEmiNumber());
        nextEMI.setAmountPaid(amountPaidSoFar + paymentDTO.getLumpSumAmount());
        nextEMI.setNumberOfEMISLeft(getNumberOfEMIsLeft(loan.getTotalAmount(), nextEMI.getAmountPaid(), loan.getEmiAmount()));
        return emiRepository.save(nextEMI);
    }

    private int getNumberOfEMIsLeft(Integer totalAmount, Integer amountPaidSoFar, Integer emiAmount) {
        return ((int) Math.ceil(((double) (totalAmount - amountPaidSoFar)) / ((double) emiAmount)));
    }

    private int getAmountPaidSoFar(Long loanId, Integer emiNumber) {
        List<EMI> lastEMIs = emiRepository.findEMIByLoanIdAndEMINumber(loanId, emiNumber);
        if(lastEMIs.isEmpty()) {
            return 0;
        } else {
            return lastEMIs.get(0).getAmountPaid();
        }
    }
}