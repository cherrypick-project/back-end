package com.cherrypick.backend.common.config;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

@EnableJpaAuditing
@Configuration
public class JpaConfig implements AuditorAware<String> {

  private static final List<String> AUTH_LIST =
    List.of("ROLE_USER", "ROLE_ADMIN", "ROLE_MEMBERSHIP");

  @Override
  public Optional<String> getCurrentAuditor() {
    return Optional.ofNullable(SecurityContextHolder.getContext())
      .map(SecurityContext::getAuthentication)
      .map(authentication -> {
        Collection<? extends GrantedAuthority> auth = authentication.getAuthorities();
        boolean isExist = AUTH_LIST.stream()
          .anyMatch(auth::contains);
        if (isExist) {
          return authentication.getName();
        }
        return null;
      });
  }
}

