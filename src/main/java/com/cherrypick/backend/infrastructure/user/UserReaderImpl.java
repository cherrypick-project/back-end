package com.cherrypick.backend.infrastructure.user;

import com.cherrypick.backend.domain.user.User;
import com.cherrypick.backend.domain.user.UserInfo;
import com.cherrypick.backend.domain.user.UserReader;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserReaderImpl implements UserReader {

  private final UserRepository userRepository;
  private final UserRepositoryQueryDsl userRepositoryQueryDsl;

  @Override
  public Optional<User> findByProviderId(String providerId) {
    return userRepository.findByProviderId(providerId);
  }

  @Override
  public Page<UserInfo.User> findByUsers(String searchName, Pageable pageable) {
    return userRepositoryQueryDsl.findByUsers(searchName, pageable);
  }
}
