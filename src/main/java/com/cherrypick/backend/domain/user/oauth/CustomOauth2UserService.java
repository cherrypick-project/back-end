package com.cherrypick.backend.domain.user.oauth;

import com.cherrypick.backend.common.exception.ErrorCode;
import com.cherrypick.backend.common.exception.UnAuthorizedException;
import com.cherrypick.backend.domain.user.User;
import com.cherrypick.backend.domain.user.UserReader;
import com.cherrypick.backend.domain.user.UserStore;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomOauth2UserService extends DefaultOAuth2UserService {

  private final UserReader userReader;

  private final UserStore userStore;

  private final PasswordEncoder passwordEncoder;

  @Override
  public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest)
    throws OAuth2AuthenticationException {
    OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);

    try {
      return process(oAuth2UserRequest, oAuth2User);
    } catch (AuthenticationException ex) {
      throw ex;
    } catch (Exception ex) {
      // Throwing an instance of AuthenticationException will trigger the OAuth2AuthenticationFailureHandler
      throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
    }
  }

  //인증을 요청하는 사용자에 따라서 없는 회원이면 회원가입, 이미 존재하는 회원이면 업데이트를 실행한다.
  private OAuth2User process(OAuth2UserRequest oAuth2UserRequest, OAuth2User user) {
    ProviderType providerType = ProviderType.valueOf(
      oAuth2UserRequest.getClientRegistration().getRegistrationId().toUpperCase());
    OAuth2UserInfo userInfo = providerType.getOauth2UserInfo(
      user.getAttributes());
    //provider타입에 따라서 각각 다르게 userInfo가져온다. (가져온 필요한 정보는 OAuth2UserInfo로 동일하다)


    Optional<User> userOptional = userReader.findByProviderId(
        providerType + userInfo.getId());
    User savedUser;
    if (userOptional.isPresent()) {
      savedUser = userOptional.get();
      valid(providerType, savedUser);
    } else {
      savedUser = createUser(userInfo, providerType);
    }
    return new JwtUserDetails(savedUser, user.getAttributes());
  }

  private void valid(ProviderType providerType, User savedUser) {
    if (providerType != savedUser.getProviderType()) {
      throw new UnAuthorizedException(
        "Looks like you're signed up with " + providerType +
          " account. Please use your " + savedUser.getProviderType() + " account to login.",
        ErrorCode.UNAUTHORIZED
      );
    }
    if (!savedUser.isActivated()) {
      throw new RuntimeException(savedUser.getEmail() + " -> 활성화되어 있지 않습니다.");
    }
  }

  //넘어온 사용자 정보를 통해서 회원가입을 실행한다.
  private User createUser(OAuth2UserInfo userInfo, ProviderType providerType) {
    String password = passwordEncoder.encode("cherryPickOauth");
    User user = User.OauthSignUp(providerType + userInfo.getId(), userInfo.getEmail(), password, userInfo.getName() ,
        providerType);
    return userStore.store(user);
  }
}

