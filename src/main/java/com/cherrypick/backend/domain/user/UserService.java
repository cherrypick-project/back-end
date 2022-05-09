package com.cherrypick.backend.domain.user;

public interface UserService {

  UserInfo.Token authorize(UserCommand.UserLoginRequest command);

  UserInfo.Token reissue(UserCommand.ReissueRequest command);

  UserInfo.Token signUp(UserCommand.SignUpRequest command);

  UserInfo.Profile inquiryUserProfile(String loginId);

  UserInfo.SignOut signOut(String loginId);
}
