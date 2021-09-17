package com.application.service;

import com.application.model.dto.PaymentDTO;
import com.application.model.entity.EMI;

public interface PaymentService {

    EMI payEMI(PaymentDTO paymentDTO) throws Exception;
}