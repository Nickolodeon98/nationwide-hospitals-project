package com.practice.springbootdocker.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {

  private static final Long period = 1000L * 60 * 60;

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


  public static Claims extractClaims(String token, String secretKey) {
    Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    return claims;
  }

  public static boolean isExpired(String token, String secretKey) {
    Claims claimsToCheck = extractClaims(token, secretKey);
    if (claimsToCheck.getExpiration().before(new Date())) return true;
    return false;
  }

  public static String getUserName(String token, String secretKey) {
    return extractClaims(token, secretKey).getSubject();
  }

  public static Authentication getAuthentication(String userName) {
    return new UsernamePasswordAuthenticationToken(userName, null, List.of(new SimpleGrantedAuthority("USER")));
  }
}
