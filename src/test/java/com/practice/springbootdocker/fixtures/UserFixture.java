package com.practice.springbootdocker.fixtures;

import com.practice.springbootdocker.domain.entity.User;

public class UserFixture {

  public static User getUser() {
    return User.builder()
        .userName("sjeon0730")
        .password("Ingod2013!")
        .build();
  }
}
