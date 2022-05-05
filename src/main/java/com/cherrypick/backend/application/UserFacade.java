package com.cherrypick.backend.application;

import com.cherrypick.backend.domain.user.UserInfo;
import com.cherrypick.backend.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserFacade {

  private final UserService userService;

  @Transactional(readOnly = true)
  public UserInfo.Profile inquiryUserProfile(String loginId) {
    return userService.inquiryUserProfile(loginId);
  }

  @Transactional
  public UserInfo.SignOut signOut(String loginId) {
    return userService.signOut(loginId);
  }
}
