package com.application;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import com.application.model.entity.EMI;
import com.application.model.entity.Loan;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PaymentOperation {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static void payEMIs(Loan loan, Map<Integer, Integer> amountByEMINumber) throws Exception {
        Integer totalAmount = loan.getTotalAmount(), emiAmount = loan.getEmiAmount(), amountPaid = 0, emiNumber = 0, lumpSumAmount = 0;
        while(amountPaid < totalAmount) {
            if(emiNumber > 0) {
                lumpSumAmount = (Math.min(emiAmount, (totalAmount - amountPaid)) + amountByEMINumber.getOrDefault((emiNumber + 1), 0));
            } else {
                lumpSumAmount = amountByEMINumber.getOrDefault((emiNumber + 1), 0);
            }
            URL url = new URL("http://localhost:8090/payment/emi");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setDoOutput(true);
            String jsonInputString = "{\"bankName\": \"" + loan.getBankName() + 
                    "\", \"borrowerName\": \"" + loan.getBorrowerName() + 
                    "\", \"lumpSumAmount\": \"" + lumpSumAmount + 
                    "\", \"emiNumber\": \"" + emiNumber + 
                    "\"}";
            try(OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }
            InputStream responseStream = connection.getInputStream();
            EMI emi = MAPPER.readValue(responseStream, EMI.class);
            amountPaid = emi.getAmountPaid();
            emiNumber++;
        }
    }
}