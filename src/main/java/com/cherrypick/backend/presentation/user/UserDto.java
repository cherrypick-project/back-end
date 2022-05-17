package com.cherrypick.backend.presentation.user;

import com.cherrypick.backend.domain.user.Authority;
import com.cherrypick.backend.domain.user.User.Career;
import com.cherrypick.backend.domain.user.oauth.ProviderType;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class UserDto {

  @Getter
  @AllArgsConstructor
  public static class ProfileResponse {
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
  public static class SignOutResponse {
    private String email;
  }
}
