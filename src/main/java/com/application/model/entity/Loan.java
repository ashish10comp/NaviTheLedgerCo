package com.application.model.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table
@Getter
@Setter
@ToString
public class Loan implements Serializable {

    private static final long serialVersionUID = 5L;

    @Id
    @GeneratedValue
    private Long loanId;

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

    @NonNull
    private Integer totalAmount;

    @NonNull
    private Integer emiAmount;
}