package com.application.model.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BalanceDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NonNull
    private String bankName;

    @NonNull
    private String borrowerName;

    @NonNull
    private Integer emiNumber;

    @NonNull
    private Integer numberOfEMISLeft;

    @NonNull
    private Integer amountPaid;
}