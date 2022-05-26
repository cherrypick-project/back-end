package com.cherrypick.backend.domain.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.cherrypick.backend.common.exception.BusinessException;
import com.cherrypick.backend.common.exception.ErrorCode;
import com.cherrypick.backend.common.exception.UnAuthorizedException;
import com.cherrypick.backend.common.jwt.TokenProvider;
import com.cherrypick.backend.domain.user.User.Career;
import com.cherrypick.backend.domain.user.User.KnownPath;
import com.cherrypick.backend.domain.user.UserCommand.ReissueRequest;
import com.cherrypick.backend.domain.user.UserCommand.SignUpRequest;
import com.cherrypick.backend.domain.user.UserCommand.UserLoginRequest;
import com.cherrypick.backend.domain.user.UserInfo.Profile;
import com.cherrypick.backend.domain.user.UserInfo.SignOut;
import com.cherrypick.backend.domain.user.UserInfo.Token;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

  @Autowired
  private UserService userService;
  @Autowired
  private TokenProvider tokenProvider;


  @DisplayName("유저의 정보로 인증을 한뒤 JWT 토큰을 발행한다")
  @Test
  void authorize() {
    UserLoginRequest command = new UserLoginRequest("admin", "admin");

    Token token = userService.authorize(command);
    Authentication authentication = tokenProvider.getAuthentication(token.getAccessToken());

    assertThat(tokenProvider.validateToken(token.getAccessToken())).isTrue();
    assertThat(authentication.getName()).isEqualTo(command.getProviderId());
    assertThat(authentication.getAuthorities()).isEqualTo(
        List.of(new SimpleGrantedAuthority(Authority.ROLE_ADMIN.name())));
  }

  @DisplayName("RefreshToken을 통해 JWT토큰을 재발행 한다")
  @Test
  void reissue() {
    UserLoginRequest loginCommand = new UserLoginRequest("admin", "admin");
    Token token = userService.authorize(loginCommand);
    ReissueRequest reissueCommand = new ReissueRequest(token.getAccessToken(),
        token.getRefreshToken());

    Token reissueToken = userService.reissue(reissueCommand);
    Authentication authentication = tokenProvider.getAuthentication(reissueToken.getAccessToken());

    assertThat(tokenProvider.validateToken(reissueToken.getAccessToken())).isTrue();
    assertThat(authentication.getName()).isEqualTo(loginCommand.getProviderId());
    assertThat(authentication.getAuthorities()).isEqualTo(
        List.of(new SimpleGrantedAuthority(Authority.ROLE_ADMIN.name())));
  }

  @DisplayName("유효하지 않은 RefreshToken으로 재발행을 할 경우 예외 발생")
  @Test
  void isNotValidRefreshToken() {
    String accessToken = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImNwIjoiUk9MRV9BRE1JTiIsImV4cCI6MTY0Nzk1ODU3MX0.QNY5uhQTj4rMeUoQmvkb8MjYokyVevEnEzrPI1y7-dhqY32uSXmV8Gn4Xvt9ivNoKnxSTfOqVCh3kWWomVEKGw";
    String refreshToken = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImNwIjoiUk9MRV9BRE1JTiIsImV4cCI6MTY0OTE2MDk3MX0.yEZiIJZX_EwOlOGgEuhtSz8UjT6wOTGb8QakuLL6O8fn_4TOemsEe8nXxW0VXN0cGwlBHPz1t_O6lEKGEIqB8g";

    ReissueRequest reissueCommand = new ReissueRequest(accessToken, refreshToken);

    assertThatThrownBy(() -> userService.reissue(reissueCommand))
        .isInstanceOf(UnAuthorizedException.class)
        .hasMessage("유효하지 않은 RefreshToken 입니다.");
  }

  @DisplayName("회원의 추가정보를 저장하고 JWT 토큰을 발행한다")
  @Test
  void signUp() {
    SignUpRequest command = new SignUpRequest("unSignedUser", "BackEnd", Career.LESS_THAN_3YEARS,
        KnownPath.SEARCH);

    Token token = userService.signUp(command);
    Authentication authentication = tokenProvider.getAuthentication(token.getAccessToken());

    assertThat(tokenProvider.validateToken(token.getAccessToken())).isTrue();
    assertThat(authentication.getName()).isEqualTo(command.getProviderId());
    assertThat(authentication.getAuthorities()).isEqualTo(
        List.of(new SimpleGrantedAuthority(Authority.ROLE_USER.name())));
  }

  @DisplayName("회원의 추가정보를 저장시 회원정보가 없으면 예외가 발생한다")
  @Test
  void signUp_exception() {
    SignUpRequest command = new SignUpRequest("unSignedUser1", "BackEnd", Career.LESS_THAN_3YEARS,
        KnownPath.SEARCH);

    assertThatThrownBy(() -> userService.signUp(command))
        .isInstanceOf(BusinessException.class)
        .hasMessage(command.getProviderId() + " 사용자를 찾지 못했습니다.");
  }

  @DisplayName("유저 프로필 정보를 조회한다")
  @Test
  void inquiryUserProfile() {
    String loginId = "user";

    Profile profile = userService.inquiryUserProfile(loginId);

    assertThat(profile.getAuthority()).isEqualTo(Authority.ROLE_USER);
    assertThat(profile.getEmail()).isEqualTo("user@naver.com");
    assertThat(profile.getJob()).isEqualTo("백엔드");
    assertThat(profile.getNickname()).isEqualTo("user");
    assertThat(profile.getCareer()).isEqualTo(Career.LESS_THAN_3YEARS);
    assertThat(profile.getKnownPath()).isEqualTo(KnownPath.SEARCH);
  }

  @DisplayName("유저프로필을 찾지 못하면 예외발생")
  @Test
  void notFoundUserProfile() {
    String loginId = "user1";

    assertThatThrownBy(() -> userService.inquiryUserProfile(loginId))
        .isInstanceOf(BusinessException.class)
        .hasMessage(loginId + " 사용자를 찾지 못했습니다.");
  }

  @DisplayName("회원 탈퇴한 유저프로필 조회 시 예외발생")
  @Test
  void notActivatedUserProfile() {
    String loginId = "nonActiveUser";

    assertThatThrownBy(() -> userService.inquiryUserProfile(loginId))
        .isInstanceOf(BusinessException.class)
        .hasMessage(ErrorCode.NOT_ACTIVE_ACCOUNT.getMessage());
  }

  @DisplayName("계정 탈퇴를 한다")
  @Test
  void signOut() {
    String loginId = "user";

    SignOut signOut = userService.signOut(loginId);

    assertThat(signOut.getEmail()).isEqualTo("user@naver.com");
  }

  @DisplayName("가입하지 않은 유저 ID로 계정 탈퇴 시도시 예외발생")
  @Test
  void isNotValidSignOut() {
    String loginId = "notUser";

    assertThatThrownBy(() -> userService.signOut(loginId))
        .isInstanceOf(BusinessException.class)
        .hasMessage(loginId + " 사용자를 찾지 못했습니다.");
  }
}