package com.cherrypick.backend.domain.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

import com.cherrypick.backend.common.exception.BusinessException;
import com.cherrypick.backend.common.jwt.TokenProvider;
import com.cherrypick.backend.domain.user.User.Career;
import com.cherrypick.backend.domain.user.UserCommand.SignUpRequest;
import com.cherrypick.backend.domain.user.UserInfo.Token;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


@SpringBootTest
class UserServiceImplTest {

  @Autowired
  private UserService userService;

  @Autowired
  private TokenProvider tokenProvider;

  @DisplayName("회원의 추가정보를 저장하고 JWT 토큰을 발행한다")
  @Test
  void signup() {
    SignUpRequest command = new SignUpRequest("unSignedUser", "BackEnd", Career.LESS_THAN_3YEARS, "Search");

    Token token = userService.signup(command);
    Authentication authentication = tokenProvider.getAuthentication(token.getAccessToken());


    assertThat(tokenProvider.validateToken(token.getAccessToken())).isTrue();
    assertThat(authentication.getName()).isEqualTo(command.getProviderId());
    assertThat(authentication.getAuthorities()).isEqualTo(List.of(new SimpleGrantedAuthority(Authority.ROLE_USER.name())));
  }

  @DisplayName("회원의 추가정보를 저장시 회원정보가 없으면 예외가 발생한다.")
  @Test
  void signup_exception() {
    SignUpRequest command = new SignUpRequest("unSignedUser1", "BackEnd", Career.LESS_THAN_3YEARS, "Search");

    assertThatThrownBy(
        ()-> userService.signup(command)
    ).isInstanceOf(BusinessException.class).hasMessage(command.getProviderId()+" 사용자를 찾지 못했습니다.");
  }
}