package com.cherrypick.backend.domain.user;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserReader {

  Optional<User> findByProviderId(String providerId);

  Page<UserInfo.User> findByUsers(String searchName, Pageable pageable);

  UserInfo.Statistics findByStatistics();
}
