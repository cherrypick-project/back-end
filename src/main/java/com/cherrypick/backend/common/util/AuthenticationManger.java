package com.cherrypick.backend.common.util;

import com.cherrypick.backend.domain.user.User;
import com.cherrypick.backend.domain.user.UserCommand;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthenticationManger {

  private final AuthenticationManagerBuilder authenticationManagerBuilder;

  public Authentication createAuthentication(UserCommand.UserLoginRequest command) {
    UsernamePasswordAuthenticationToken authenticationToken =
      new UsernamePasswordAuthenticationToken(command.getProviderId(), command.getPassword());
    return authenticationManagerBuilder.getObject()
      .authenticate(authenticationToken);
  }

  public Authentication changeAuthority(User user) {
    List<GrantedAuthority> authorities = List.of(
      new SimpleGrantedAuthority(user.getAuthority().name()));
    return new UsernamePasswordAuthenticationToken(user.getProviderId(),
      user.getPassword(), authorities);
  }
}
