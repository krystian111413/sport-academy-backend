package com.sportacademy.project.auth;

import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

@Service
public class AuthService {

  public Boolean isAuthorization(String header) {
    String login = System.getenv("login");
    String pwd = System.getenv("pwd");
    String loginAndpwd = new String(Base64Utils.decodeFromString(header));
    return loginAndpwd.equals(login + "&" + pwd);
  }

  public Boolean checkCredentials(AuthRequest authRequest) {
    String login = System.getenv("login");
    String pwd = System.getenv("pwd");
    return authRequest.getLogin().equals(login) && authRequest.getPassword().equals(pwd);
  }
}
