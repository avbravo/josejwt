/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.josejwt;

import java.util.Map;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.keys.HmacKey;

/**
 *
 * @author avbravo
 */
public class Consumer {
    public void consumir(){
        try {
             String jwt = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWV9.TJVA95OrM7E2cBab30RMHrHDcEfxjoYZgeFONFh7HgQ";
        String secret = "secret";

        JwtConsumer jwtConsumer = new JwtConsumerBuilder()
                .setVerificationKey(new HmacKey(secret.getBytes())) //what kind of key do i need to use it here?
                .build();


        JwtClaims jwtClaims = jwtConsumer.processToClaims(jwt);
        Map<String, Object> claimsMap = jwtClaims.getClaimsMap();

        claimsMap.forEach((String key, Object val) -> {
            System.out.println(key + ": " + val.toString());
        });

        } catch (Exception e) {
            System.out.println("---> ");
        }
    }
}
