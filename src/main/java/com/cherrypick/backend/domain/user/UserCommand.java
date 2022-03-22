package com.cherrypick.backend.domain.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class UserCommand {

  @Getter
  @AllArgsConstructor
  public static class UserLoginRequest {
    private String providerId;
    private String password;
  }

  @Getter
  @AllArgsConstructor
  public static class ReissueRequest {
    private String accessToken;
    private String refreshToken;
  }
}
