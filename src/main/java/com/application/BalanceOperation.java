package com.application;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import com.application.model.dto.BalanceDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BalanceOperation {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static void printBalances(List<String> balanceInstructions) throws Exception {
        for(String balanceInstruction : balanceInstructions) {
            String[] balanceInstructionAttributes = balanceInstruction.split(" ");
            URL url = new URL("http://localhost:8090/balance/emi");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setDoOutput(true);
            String jsonInputString = "{\"bankName\": \"" + balanceInstructionAttributes[1] + 
                    "\", \"borrowerName\": \"" + balanceInstructionAttributes[2] + 
                    "\", \"emiNumber\": \"" + balanceInstructionAttributes[3] + 
                    "\"}";
            try(OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }
            InputStream responseStream = connection.getInputStream();
            BalanceDTO balanceDTO = MAPPER.readValue(responseStream, BalanceDTO.class);
            System.out.println(balanceDTO.getBankName() + " " + balanceDTO.getBorrowerName() + " " + balanceDTO.getAmountPaid() + " " + balanceDTO.getNumberOfEMISLeft());
        }
    }
}