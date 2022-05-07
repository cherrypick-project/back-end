package com.cherrypick.backend.domain.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class UserInfo {

  @Getter
  @AllArgsConstructor
  public static class Token {

    private String accessToken;
    private String refreshToken;
  }
}
