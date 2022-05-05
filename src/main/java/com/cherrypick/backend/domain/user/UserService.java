package com.cherrypick.backend.domain.user;

import com.cherrypick.backend.domain.user.UserInfo.SignOut;

public interface UserService {

  UserInfo.Token authorize(UserCommand.UserLoginRequest command);

  UserInfo.Token reissue(UserCommand.ReissueRequest command);

  UserInfo.Token signup(UserCommand.SignUpRequest command);

  UserInfo.Profile searchUserProfile(String loginId);

  UserInfo.SignOut signOut(String loginId);
}
