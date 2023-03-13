package com.practice.springbootdocker.service;

import com.practice.springbootdocker.domain.dto.JoinRequest;
import com.practice.springbootdocker.domain.dto.JoinResponse;
import com.practice.springbootdocker.domain.entity.User;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;

  public JoinResponse registerUser(JoinRequest joinRequest) {
    User savedUser = userRepository.save(joinRequest.toEntity());
    return JoinResponse.of(savedUser);
  }
}
