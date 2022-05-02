package com.cherrypick.backend.domain.user;

import com.cherrypick.backend.common.exception.BusinessException;
import com.cherrypick.backend.common.exception.ErrorCode;
import com.cherrypick.backend.common.jwt.TokenProvider;
import com.cherrypick.backend.domain.user.UserCommand.ReissueRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final TokenProvider tokenProvider;
  private final AuthenticationManagerBuilder authenticationManagerBuilder;
  private final UserReader reader;

  @Override
  public UserInfo.Token authorize(UserCommand.UserLoginRequest command) {
    UsernamePasswordAuthenticationToken authenticationToken =
      new UsernamePasswordAuthenticationToken(command.getProviderId(), command.getPassword());

    Authentication authentication = authenticationManagerBuilder.getObject()
      .authenticate(authenticationToken);
    SecurityContextHolder.getContext().setAuthentication(authentication);

    return tokenProvider.createTokens(authentication);
  }

  @Override
  public UserInfo.Token reissue(ReissueRequest command) {
    if (!tokenProvider.validateToken(command.getRefreshToken())) {
      throw new RuntimeException("유효하지 않은 RefreshToken 입니다."); // 나중에 controllerAdvice랑 다시 합쳐야함
    }

    Authentication authentication = tokenProvider.getAuthentication(command.getAccessToken());
    SecurityContextHolder.getContext().setAuthentication(authentication);

    return tokenProvider.createTokens(authentication);
  }

  @Override
  public UserInfo.Token signup(UserCommand.SignUpRequest command) {
    User user = reader.findByProviderId(command.getProviderId()).orElseThrow(() -> new BusinessException(command.getProviderId()+" 사용자를 찾지 못했습니다.",
        ErrorCode.NOT_FOUND_USER));
    user.addUserInfo(command);

    List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(user.getAuthority().name()));
    Authentication newAuth = new UsernamePasswordAuthenticationToken(user.getProviderId(), user.getPassword(), authorities);
    SecurityContextHolder.getContext().setAuthentication(newAuth);
    return tokenProvider.createTokens(newAuth);
  }
}
