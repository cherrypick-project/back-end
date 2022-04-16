package com.cherrypick.backend.domain.user;

public interface UserService {

  UserInfo.Token authorize(UserCommand.UserLoginRequest command);

  UserInfo.Token reissue(UserCommand.ReissueRequest command);

  UserInfo.Token signup(UserCommand.SignUpRequest command);
}
