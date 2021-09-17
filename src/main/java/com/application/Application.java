package com.application;

import java.io.FileInputStream;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import com.application.config.AppConfig;
import com.application.model.entity.Loan;

@SpringBootApplication
@Import(AppConfig.class)
public class Application {
    private static final String LOAN = "LOAN";
    private static final String PAYMENT = "PAYMENT";
    private static final String BALANCE = "BALANCE";

    private static class Instruction {
        String loanInstruction;
        Map<Integer, Integer> amountByEMINumber;

        public Instruction() {
            loanInstruction = "";
            amountByEMINumber = new HashMap<>();
        }
    }

    public static void main(String[] args) throws Exception {
        startApplication(args);
        List<String> balanceInstructions = new ArrayList<>();
        Map<Map.Entry<String, String>, Instruction> instructionByLenderBorrower = new HashMap<>();
        populateOperations(args[0], balanceInstructions, instructionByLenderBorrower);
        executeOperations(balanceInstructions, instructionByLenderBorrower);
    }

    private static void startApplication(String[] args) {
        SpringApplication app = new SpringApplication(Application.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }

    private static void populateOperations(String filePath, List<String> balanceInstructions,
            Map<Map.Entry<String, String>, Instruction> instructionByLenderBorrower) throws Exception {
        String instructionLine = "";
        String[] instructionAttributes = null;
        Instruction instruction = null;
        Map.Entry<String, String> lenderBorrower = null;
        FileInputStream fileInputStream = new FileInputStream(filePath);
        @SuppressWarnings("resource")
        Scanner scanner = new Scanner(fileInputStream);
        while(scanner.hasNextLine()) {
            instructionLine = scanner.nextLine();
            instructionAttributes = instructionLine.split(" ");
            if(BALANCE.equals(instructionAttributes[0])) {
                balanceInstructions.add(instructionLine);
            } else {
                lenderBorrower = new AbstractMap.SimpleImmutableEntry<>(instructionAttributes[1], instructionAttributes[2]);
                instruction = instructionByLenderBorrower.getOrDefault(lenderBorrower, new Instruction());
                if(LOAN.equals(instructionAttributes[0])) {
                    instruction.loanInstruction = instructionLine;
                } else if(PAYMENT.equals(instructionAttributes[0])) {
                    instruction.amountByEMINumber.put(Integer.parseInt(
                            instructionAttributes[4]), (instruction.amountByEMINumber.getOrDefault(
                                    Integer.parseInt(instructionAttributes[4]), 0) + Integer.parseInt(instructionAttributes[3])));
                } else {
                    throw new Exception("Unsupported Instruction!!" + instructionLine);
                }
                instructionByLenderBorrower.put(lenderBorrower, instruction);
            }
        }
        scanner.close();
    }

    private static void executeOperations(List<String> balanceInstructions,
            Map<Map.Entry<String, String>, Instruction> instructionByLenderBorrower) throws Exception {
        for(Map.Entry<Map.Entry<String, String>, Instruction> entry : instructionByLenderBorrower.entrySet()) {
            Loan loan = LoanOperation.createLoan(entry.getValue().loanInstruction);
            PaymentOperation.payEMIs(loan, entry.getValue().amountByEMINumber);
        }
        BalanceOperation.printBalances(balanceInstructions);
    }
}