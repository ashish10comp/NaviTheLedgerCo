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
public class EMI implements Serializable {

    private static final long serialVersionUID = 4L;

    @Id
    @GeneratedValue
    private Long emiId;

    @NonNull
    private Long loanId;

    @NonNull
    private Integer emiNumber;

    @NonNull
    private Integer numberOfEMISLeft;

    @NonNull
    private Integer amountPaid;
}