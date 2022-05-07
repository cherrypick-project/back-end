package com.cherrypick.backend.presentation.auth;

import com.cherrypick.backend.domain.user.User.Career;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

public class AuthDto {

  @Getter
  @AllArgsConstructor
  public static class LoginRequest {

    private String providerId;
    private String password;
  }

  @Getter
  @AllArgsConstructor
  public static class ReissueRequest {

    private String accessToken;
    private String refreshToken;
  }

  @Getter
  @ToString
  @AllArgsConstructor
  public static class LoginResponse {

    private String accessToken;
    private String refreshToken;
  }

  @Getter
  @Setter
  @ToString
  @AllArgsConstructor
  public static class SignUpRequest {

    private String providerId;
    @NotNull
    private String job;

    @JsonProperty("career")
    @NotNull
    private Career career;
    @NotNull
    private String knownPath;
  }

  @Getter
  @ToString
  @AllArgsConstructor
  public static class SignUpResponse {

    private String accessToken;
    private String refreshToken;
  }
}
