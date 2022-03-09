package com.cherrypick.backend.application;

import com.cherrypick.backend.domain.user.UserCommand;
import com.cherrypick.backend.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthFacade {

  private final UserService userService;

  public String login(UserCommand.UserLoginRequest request) {
    var token = userService.login(request);
    return token;
  }
}
