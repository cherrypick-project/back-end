package com.cherrypick.backend.infrastructure.user;

import com.cherrypick.backend.domain.user.User;
import com.cherrypick.backend.domain.user.UserReader;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserReaderImpl implements UserReader {

  private final UserRepository userRepository;

  @Override
  public Optional<User> findWithAuthortyByProviderId(String providerId) {
    return userRepository.findWithAuthortyByProviderId(providerId);
  }


}
