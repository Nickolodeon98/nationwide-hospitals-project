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
public class JoinResponse {
  private String message;


  public static JoinResponse of(User user) {
    return JoinResponse.builder()
        .message(String.format("%s 님 환영합니다.", user.getUserName()))
        .build();
  }
}
