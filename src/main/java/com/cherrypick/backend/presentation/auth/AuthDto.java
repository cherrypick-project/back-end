package com.cherrypick.backend.presentation.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

public class AuthDto {

  @Getter
  @ToString
  @AllArgsConstructor
  public static class LoginResponse {

    private String token;
  }
}
