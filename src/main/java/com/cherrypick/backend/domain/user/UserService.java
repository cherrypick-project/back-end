package com.cherrypick.backend.domain.user;

public interface UserService {

  String login(UserCommand.UserLoginRequest request);
}
