package com.application.service;

import com.application.model.dto.BalanceDTO;

public interface BalanceService {

    BalanceDTO getBalanceEMI(BalanceDTO balanceDTO) throws Exception;
}