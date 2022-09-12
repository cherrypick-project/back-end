package com.cherrypick.backend.domain.user;

import com.cherrypick.backend.common.exception.BusinessException;
import com.cherrypick.backend.common.exception.ErrorCode;
import com.cherrypick.backend.common.exception.UnAuthorizedException;
import com.cherrypick.backend.common.jwt.TokenProvider;
import com.cherrypick.backend.common.util.AuthenticationManger;
import com.cherrypick.backend.domain.user.UserCommand.ReissueRequest;
import com.cherrypick.backend.domain.user.UserInfo.Token;
import com.cherrypick.backend.infrastructure.redis.RedisRepository;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final TokenProvider tokenProvider;
  private final UserReader reader;
  private final RedisRepository redisRepository;
  private final AuthenticationManger authenticationManger;
  private final UserInfoMapper userInfoMapper;

  @Value("${jwt.refresh-token-validity-in-seconds}")
  private long refreshTokenValidityInMilliseconds;

  private static final String ID_PREFIX = "RT:";

  @Override
  public UserInfo.Token authorize(UserCommand.UserLoginRequest command) {
    Authentication authentication = authenticationManger.createAuthentication(command);

    Token token = tokenProvider.createTokens(authentication);
    redisRepository.setValue(
      ID_PREFIX + command.getProviderId(),
      token.getRefreshToken(),
      refreshTokenValidityInMilliseconds,
      TimeUnit.MILLISECONDS);

    return token;
  }

  @Override
  public UserInfo.Token reissue(ReissueRequest command) {
    if (!tokenProvider.validateToken(command.getRefreshToken())) {
      throw new UnAuthorizedException("유효하지 않은 RefreshToken 입니다.", ErrorCode.UNAUTHORIZED);
    }

    val loginId = tokenProvider.getUsernameFromToken(command.getRefreshToken());
    val redisRefreshToken = redisRepository.getValue(ID_PREFIX + loginId)
      .orElseThrow(
        () -> new UnAuthorizedException("로그인 시간이 만료되었습니다.", ErrorCode.UNAUTHORIZED));

    if (!redisRefreshToken.equals(command.getRefreshToken())) {
      throw new UnAuthorizedException("유효하지 않은 RefreshToken 입니다.", ErrorCode.UNAUTHORIZED);
    }

    Authentication authentication = tokenProvider.getAuthentication(command.getAccessToken());

    Token token = tokenProvider.createTokens(authentication);
    redisRepository.setValue(
      ID_PREFIX + loginId,
      token.getRefreshToken(),
      refreshTokenValidityInMilliseconds,
      TimeUnit.MILLISECONDS);

    return token;
  }

  @Override
  @Transactional
  public UserInfo.Token signUp(UserCommand.SignUpRequest command) {
    User user = reader.findByProviderId(command.getProviderId())
      .orElseThrow(() -> new BusinessException(command.getProviderId() + " 사용자를 찾지 못했습니다.",
        ErrorCode.NOT_FOUND_USER));
    user.addUserInfo(command);

    Authentication changedAuth = authenticationManger.changeAuthority(user);

    Token token = tokenProvider.createTokens(changedAuth);
    redisRepository.setValue(
      ID_PREFIX + user.getProviderId(),
      token.getRefreshToken(),
      refreshTokenValidityInMilliseconds,
      TimeUnit.MILLISECONDS);

    return token;
  }

  @Override
  @Transactional(readOnly = true)
  public UserInfo.Profile inquiryUserProfile(String loginId) {
    User user = reader.findByProviderId(loginId)
      .orElseThrow(() -> new BusinessException(loginId + " 사용자를 찾지 못했습니다.",
        ErrorCode.NOT_FOUND_USER));

    if (!user.isActivated()) {
      throw new BusinessException(ErrorCode.NOT_ACTIVE_ACCOUNT);
    }
    return userInfoMapper.of(user);
  }

  @Override
  @Transactional
  public UserInfo.SignOut signOut(String loginId) {
    User user = reader.findByProviderId(loginId)
      .orElseThrow(() -> new BusinessException(loginId + " 사용자를 찾지 못했습니다.",
        ErrorCode.NOT_FOUND_USER));

    user.signOut();
    return new UserInfo.SignOut(user.getEmail());
  }

  @Override
  public Page<UserInfo.User> inquiryUsers(String searchName, Pageable pageable) {
    return reader.findByUsers(searchName, pageable);
  }
}
