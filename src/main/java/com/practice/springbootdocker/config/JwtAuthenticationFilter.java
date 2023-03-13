package com.practice.springbootdocker.config;

import com.practice.springbootdocker.service.UserService;
import com.practice.springbootdocker.utils.JwtTokenProvider;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final UserService userService;
  private final String secretKey;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

    if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
      filterChain.doFilter(request, response);
      return;
    }
    String token = "";

    try {
      token = authorizationHeader.split(" ")[1];
    } catch (Exception e) {
      filterChain.doFilter(request, response);
      throw new RuntimeException(e);
    }


    if (JwtTokenProvider.isExpired(token, secretKey))
      filterChain.doFilter(request, response);

    String userName = JwtTokenProvider.getUserName(token, secretKey);

    SecurityContextHolder.getContext().setAuthentication(JwtTokenProvider.getAuthentication(userName));
    filterChain.doFilter(request, response);

  }
}
