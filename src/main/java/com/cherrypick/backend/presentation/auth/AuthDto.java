package com.cherrypick.backend.presentation.auth;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

public class AuthDto {

  @Getter
  public static class LoginRequest {
    @NotNull
    @Size(min = 3, max = 50)
    private String providerId;

    @NotNull
    @Size(min = 3, max = 100)
    private String password;
  }

  @Getter
  @ToString
  @AllArgsConstructor
  public static class LoginResponse {
    private String token;
  }
}
