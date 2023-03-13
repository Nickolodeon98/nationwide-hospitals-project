package com.practice.springbootdocker.controller;

import com.practice.springbootdocker.domain.dto.JoinRequest;
import com.practice.springbootdocker.domain.dto.JoinResponse;
import com.practice.springbootdocker.domain.dto.LoginRequest;
import com.practice.springbootdocker.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/user")
public class UserController {

  public UserService userService;

  @PostMapping("/join")
  public String joinUser(@ModelAttribute JoinRequest joinRequest) {
    JoinResponse registeredUser = userService.registerUser(joinRequest);
    return "redirect:/user/login";
  }

  @GetMapping("/login")
  public String loginUser(@RequestBody LoginRequest loginRequest) {
    userService.authenticateUser(loginRequest);
    return "dockerboard/list";
  }

}
