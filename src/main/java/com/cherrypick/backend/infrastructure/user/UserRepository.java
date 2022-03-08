package com.cherrypick.backend.infrastructure.user;

import com.cherrypick.backend.domain.user.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
  @EntityGraph(attributePaths = "authority")
  Optional<User> findWithAuthortyByProviderId(String providerId);
}
