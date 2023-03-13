package com.practice.springbootdocker.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {

  private static final Long period = 1000L * 60 * 60;

  @Value("${jwt.secret}")
  private String secretKey;

  public static String createToken(String secretKey, String userName) {
    Claims claims = Jwts.claims().setSubject(userName);
    Date currentDate = new Date();

    return Jwts.builder()
        .setClaims(claims)
        .setIssuedAt(currentDate)
        .setExpiration(new Date(currentDate.getTime() + period))
        .signWith(SignatureAlgorithm.HS256, secretKey)
        .compact();
  }
}
