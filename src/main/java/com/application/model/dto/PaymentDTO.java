package com.application.model.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PaymentDTO implements Serializable {

    private static final long serialVersionUID = 3L;

    @NonNull
    private String bankName;

    @NonNull
    private String borrowerName;

    @NonNull
    private Integer lumpSumAmount;

    @NonNull
    private Integer emiNumber;
}