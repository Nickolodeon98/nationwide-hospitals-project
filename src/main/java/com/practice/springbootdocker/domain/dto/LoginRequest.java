package com.practice.springbootdocker.domain.dto;

import com.practice.springbootdocker.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginRequest {

  private String userName;
  private String password;

  public static LoginRequest of(User user) {
    return LoginRequest.builder()
        .userName(user.getUserName())
        .password(user.getPassword())
        .build();
  }
}
