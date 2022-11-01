package src.com.o4s.test.apiTest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.time.Instant;

public class GenerateManagementToken {

    private void generateManagementToken() {
        Map<String, Object> payload = new HashMap<>();
        payload.put("access_key", "635fd0c54208780bf667307a");
        payload.put("type", "management");
        payload.put("version", 2);
        String token = Jwts.builder().setClaims(payload).setId(UUID.randomUUID().toString())
                .setExpiration(new Date(System.currentTimeMillis() + 86400 * 1000))
                .setIssuedAt(Date.from(Instant.ofEpochMilli(System.currentTimeMillis() - 60000)))
                .setNotBefore(new Date(System.currentTimeMillis()))
                .signWith(SignatureAlgorithm.HS256, "qbIP8GgfJBjgwwkMpk_FuXhZkOsvWCnISfIjI6yh3YNxH1eEwtZbD83f3Iduv3jo_j3YVyPB8PY0IUvvJH9isMZP-lXuFU-7F3XvYj1Wzba5Sy56-31GIepnuLdsWOFIrOtTwhPcHGwsfkLfhtBlgdjRQ4eSKpJ_aPT6N2ej_Iw=".getBytes()).compact();
        System.out.println("token= "+ token);
    }

    public static void main(String[] args) {
        GenerateManagementToken generateManagementToken=new GenerateManagementToken();
        generateManagementToken.generateManagementToken();
    }
}
