package com.application;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.application.model.entity.Loan;
import com.fasterxml.jackson.databind.ObjectMapper;

public class LoanOperation {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static Loan createLoan(String loanInstruction) throws Exception {
        String[] loanInstructionAttributes = loanInstruction.split(" ");
        URL url = new URL("http://localhost:8090/loan/create");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json; utf-8");
        connection.setDoOutput(true);
        String jsonInputString = "{\"bankName\": \"" + loanInstructionAttributes[1] + 
                "\", \"borrowerName\": \"" + loanInstructionAttributes[2] + 
                "\", \"principal\": \"" + loanInstructionAttributes[3] + 
                "\", \"years\": \"" + loanInstructionAttributes[4] + 
                "\", \"rate\": \"" + loanInstructionAttributes[5] + 
                "\"}";
        try(OutputStream os = connection.getOutputStream()) {
            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
        }
        InputStream responseStream = connection.getInputStream();
        return MAPPER.readValue(responseStream, Loan.class);
    }
}