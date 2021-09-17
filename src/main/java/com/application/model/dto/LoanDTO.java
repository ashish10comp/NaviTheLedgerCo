package com.application.model.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LoanDTO implements Serializable {

    private static final long serialVersionUID = 2L;

    @NonNull
    private String bankName;

    @NonNull
    private String borrowerName;

    @NonNull
    private Integer principal;

    @NonNull
    private Integer years;

    @NonNull
    private Integer rate;
}