package com.cherrypick.backend.application;

import com.cherrypick.backend.domain.user.UserCommand;
import com.cherrypick.backend.domain.user.UserCommand.SignUpRequest;
import com.cherrypick.backend.domain.user.UserInfo;
import com.cherrypick.backend.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthFacade {

  private final UserService userService;

  public UserInfo.Token authorize(UserCommand.UserLoginRequest request) {
    return userService.authorize(request);
  }

  public UserInfo.Token reissue(UserCommand.ReissueRequest request) {
    return userService.reissue(request);
  }

  public UserInfo.Token signUp(SignUpRequest command) {
    return userService.signUp(command);
  }
}
