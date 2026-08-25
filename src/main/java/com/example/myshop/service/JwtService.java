package com.example.myshop.service;


import java.util.Date;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.SignatureAlgorithm;



@Service
public class JwtService {
    
    private final String SECRET_KEY = "MysmartGridSuperSecretKeyForProtectingOurEnergySystem";

    public String generateToken(String username){
        long expireTime = 1000 * 60 * 60 * 2;
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expireTime);

        return Jwts.builder()
                .setSubject(username) // 把使用者的帳號藏進 Payload（載荷）
                .setIssuedAt(now)      // 標註手環發出的時間
                .setExpiration(expiryDate) // 標註手環的過期時間
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)   // 用我們背後那把不為人知的秘密鑰匙進行簽名（蓋上防偽鋼印）
                .compact(); // 壓縮打包！變成一串看似亂碼的超長字串


    }

}
