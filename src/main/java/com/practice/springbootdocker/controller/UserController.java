package com.practice.springbootdocker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/user")
public class UserController {

  public UserService userService;

  @PostMapping("/join")
  public String joinUser(@RequestBody JoinRequest joinRequest) {
    userService.registerUser(joinRequest.getUserName(), joinRequest.getPassword());


  }

  @GetMapping("/login")
  public String loginUser() {

  }

}
