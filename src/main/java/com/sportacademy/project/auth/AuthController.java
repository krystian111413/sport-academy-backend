package com.sportacademy.project.auth;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin
@AllArgsConstructor
@Slf4j
public class AuthController {
  private AuthService authService;

  @PostMapping
  public void checkCredentials(@RequestBody AuthRequest authRequest) {
    if (authService.checkCredentials(authRequest)) {
      log.info("User authorizated: {}", new Date());
    } else {
      throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);
    }
  }
}
