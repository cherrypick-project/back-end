package com.cherrypick.backend.application;

import com.cherrypick.backend.domain.user.UserInfo;
import com.cherrypick.backend.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserFacade {

  private final UserService userService;

  public UserInfo.Profile searchUserProfile(String loginId) {
    return userService.searchUserProfile(loginId);
  }
}
