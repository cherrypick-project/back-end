package com.cherrypick.backend.domain.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

  UserInfo.Token authorize(UserCommand.UserLoginRequest command);

  UserInfo.Token reissue(UserCommand.ReissueRequest command);

  UserInfo.Token signUp(UserCommand.SignUpRequest command);

  UserInfo.Profile inquiryUserProfile(String loginId);

  UserInfo.SignOut signOut(String loginId);

  Page<UserInfo.User> inquiryUsers(String searchName, Pageable pageable);

  UserInfo.Statistics inquiryStatistics();
}
