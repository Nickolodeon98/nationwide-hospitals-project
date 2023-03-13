package com.practice.springbootdocker.service;

import com.practice.springbootdocker.domain.dto.JoinRequest;
import com.practice.springbootdocker.domain.dto.JoinResponse;
import com.practice.springbootdocker.domain.dto.LoginRequest;
import com.practice.springbootdocker.domain.dto.LoginResponse;
import com.practice.springbootdocker.domain.entity.User;
import com.practice.springbootdocker.repository.UserRepository;
import com.practice.springbootdocker.utils.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;

  @Value("${jwt.secret}")
  private String secretKey;

  public JoinResponse registerUser(JoinRequest joinRequest) {
    User savedUser = userRepository.save(joinRequest.toEntity());
    return JoinResponse.of(savedUser);
  }

  public LoginResponse authenticateUser(LoginRequest loginRequest) {
    String token = JwtTokenProvider.createToken(secretKey, loginRequest.getUserName());

    return LoginResponse.builder().token(token).build();
  }
}
