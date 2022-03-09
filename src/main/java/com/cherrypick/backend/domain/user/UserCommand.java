package com.cherrypick.backend.domain.user;

import lombok.Getter;

public class UserCommand {

  @Getter
  public static class UserLoginRequest {
    private String providerId;
    private String password;
  }
}
