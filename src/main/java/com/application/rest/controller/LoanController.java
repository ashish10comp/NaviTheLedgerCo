package com.application.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.application.model.dto.LoanDTO;
import com.application.model.entity.Loan;
import com.application.service.LoanService;

@RestController
@RequestMapping(path = "/loan")
public class LoanController {

    @Autowired
    private LoanService loanService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public Loan createLoan(@RequestBody LoanDTO loanDTO) throws Exception {
        return loanService.create(loanDTO);
    }
}