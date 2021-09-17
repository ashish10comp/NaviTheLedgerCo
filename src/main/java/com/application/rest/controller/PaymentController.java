package com.application.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.application.model.dto.PaymentDTO;
import com.application.model.entity.EMI;
import com.application.service.PaymentService;

@RestController
@RequestMapping(path = "/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/emi")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public EMI payEMI(@RequestBody PaymentDTO paymentDTO) throws Exception {
        return paymentService.payEMI(paymentDTO);
    }
}