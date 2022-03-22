package com.cherrypick.backend.domain.user;

public interface UserService {

  UserInfo.Token authorize(UserCommand.UserLoginRequest command);
}
