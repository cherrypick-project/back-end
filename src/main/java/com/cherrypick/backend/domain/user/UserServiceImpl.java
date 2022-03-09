package com.cherrypick.backend.domain.user;

import com.cherrypick.backend.common.jwt.TokenProvider;
import com.cherrypick.backend.domain.user.UserCommand.UserLoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final TokenProvider tokenProvider;
  private final AuthenticationManagerBuilder authenticationManagerBuilder;

  @Override
  public String login(UserLoginRequest request) {
    UsernamePasswordAuthenticationToken authenticationToken =
      new UsernamePasswordAuthenticationToken(request.getProviderId(), request.getPassword());

    Authentication authentication = authenticationManagerBuilder.getObject()
      .authenticate(authenticationToken);
    SecurityContextHolder.getContext()
      .setAuthentication(authentication);

    return tokenProvider.createToken(authentication);
  }
}
