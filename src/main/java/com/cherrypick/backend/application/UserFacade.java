package com.cherrypick.backend.application;

import com.cherrypick.backend.domain.user.UserInfo;
import com.cherrypick.backend.domain.user.UserInfo.User;
import com.cherrypick.backend.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserFacade {

  private final UserService userService;

  public UserInfo.Profile inquiryUserProfile(String loginId) {
    return userService.inquiryUserProfile(loginId);
  }

  public UserInfo.SignOut signOut(String loginId) {
    return userService.signOut(loginId);
  }

  public Page<User> inquiryUsers(String searchName, Pageable pageable) {
    return userService.inquiryUsers(searchName, pageable);
  }
}
