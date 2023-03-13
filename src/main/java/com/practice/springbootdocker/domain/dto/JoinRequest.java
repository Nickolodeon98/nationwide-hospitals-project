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
public class JoinRequest {
  private String userName;
  private String password;

  public static JoinRequest of(User user) {
    return JoinRequest.builder()
        .userName(user.getUserName())
        .password(user.getPassword())
        .build();
  }

  public User toEntity() {
    return User.builder()
        .userName(this.userName)
        .password(this.password)
        .build();
  }

}
