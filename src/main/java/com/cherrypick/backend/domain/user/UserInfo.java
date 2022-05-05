package com.cherrypick.backend.domain.user;

import com.cherrypick.backend.domain.user.User.Career;
import com.cherrypick.backend.domain.user.oauth.ProviderType;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class UserInfo {

  @Getter
  @AllArgsConstructor
  public static class Token {

    private String accessToken;
    private String refreshToken;
  }

  @Getter
  @AllArgsConstructor
  public static class Profile {

    private String email;
    private String nickname;
    private boolean activated;
    private Authority authority;
    private ProviderType providerType;
    private String job;
    private Career career;
    private String knownPath;

  }

  @Getter
  @AllArgsConstructor
  public static class SignOut {
    private String email;
  }
}
