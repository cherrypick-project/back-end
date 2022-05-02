package com.cherrypick.backend.domain.user;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

  private final UserReader userReader;

  @Override
  @Transactional(readOnly = true)
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return userReader.findByProviderId(username).map(user -> createUser(username, user))
        .orElseThrow(() -> new UsernameNotFoundException(username + " -> 데이터베이스에서 찾을 수 없습니다."));
  }

  private org.springframework.security.core.userdetails.User createUser(String username,
      User user) {
    if (!user.isActivated()) {
      throw new RuntimeException(username + " -> 활성화되어 있지 않습니다.");
    }

    List<GrantedAuthority> grantedAuthority = new ArrayList<>(List.of(
        new SimpleGrantedAuthority(user.getAuthority().toString())));

    return new org.springframework.security.core.userdetails.User(user.getProviderId(),
        user.getPassword(),
        grantedAuthority);
  }
}