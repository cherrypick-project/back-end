package com.cherrypick.backend.presentation.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

public class AuthDto {

  @Getter
  @AllArgsConstructor
  public static class LoginRequest {

    private String providerId;
    private String password;
  }

  @Getter
  @ToString
  @AllArgsConstructor
  public static class LoginResponse {

    private String accessToken;
    private String refreshToken;
  }
}
