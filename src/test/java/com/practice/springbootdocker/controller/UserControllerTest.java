package com.practice.springbootdocker.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.springbootdocker.domain.dto.JoinRequest;
import com.practice.springbootdocker.domain.dto.JoinResponse;
import com.practice.springbootdocker.domain.dto.LoginRequest;
import com.practice.springbootdocker.domain.dto.LoginResponse;
import com.practice.springbootdocker.fixtures.UserFixture;
import com.practice.springbootdocker.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(UserController.class)
class UserControllerTest {

  @Autowired
  MockMvc mockMvc;

  @Autowired
  ObjectMapper objectMapper;

  @MockBean
  UserService userService;

  JoinRequest joinRequest;
  JoinResponse joinResponse;
  LoginRequest loginRequest;
  LoginResponse loginResponse;

  @BeforeEach
  void setUp() {
    joinRequest = JoinRequest.of(UserFixture.getUser());
    joinResponse = JoinResponse.of(UserFixture.getUser());
    loginRequest = LoginRequest.of(UserFixture.getUser());
    loginResponse = LoginResponse.builder().token("token").build();
  }

  @Test
  @WithMockUser
  @DisplayName("사용자 회원가입 성공")
  void success_join_user() throws Exception {
    given(userService.registerUser(joinRequest)).willReturn(joinResponse);

    String url = "/user/join";

    mockMvc.perform(post(url).content(objectMapper.writeValueAsBytes(joinRequest)))
        .andExpect(status().isOk())
//        .andExpect(jsonPath("$.message").value(joinRequest.getUserName() + "님 환영합니다."))
        .andDo(print());

    verify(userService).registerUser(joinRequest);
  }

  @Nested
  @DisplayName("사용자 로그인")
  class LoginUser {
    @Test
    @DisplayName("성공")
    void success_login_user() throws Exception {
      given(userService.authenticateUser(loginRequest)).willReturn(loginResponse);

      String url = "/user/login";

      mockMvc.perform(get(url).content(objectMapper.writeValueAsBytes(loginRequest)))
          .andExpect(status().isOk())
          .andDo(print());

      verify(userService).authenticateUser(loginRequest);
    }
  }


}