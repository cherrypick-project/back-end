package com.cherrypick.backend.application;

import com.cherrypick.backend.domain.user.UserCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthFacade {

  public String login(UserCommand.UserLoginRequest request) {
    var token = "";
    return token;
  }
}
