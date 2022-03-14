package com.cherrypick.backend.security.auth;

import com.cherrypick.backend.domain.user.User;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

//기본 UserDetails로는 실무에서 필요한 정보를 모두 담을 수 없기에 아래와 같은 JwtUserDetails를 구현하여 사용한다.

@Getter
public class JwtUserDetails implements UserDetails, OAuth2User {

  private final User user;

  private Map<String, Object> attributes;

  public JwtUserDetails(User user, Map<String, Object> attributes) {
    this.user = user;
    this.attributes = attributes;
  }

  @Override
  public Map<String, Object> getAttributes() {
    return attributes;
  }

  //해당 User의 권한을 리턴하는 곳
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return new ArrayList<>(List.of(
        new SimpleGrantedAuthority(user.getAuthority().toString())));
  }

  @Override
  public String getPassword() {
    return user.getPassword();
  }

  @Override
  public String getUsername() {
    return user.getEmail();
  }

  //사용자의 계정이 만료되었는지 여부를 나타냅니다. 만료된 계정은 인증할 수 없습니다.
  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  //사용자가 잠겨 있는지 또는 잠금 해제되어 있는지 나타냅니다. 잠긴 사용자는 인증할 수 없습니다.
  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  //사용자의 자격 증명(암호)이 만료되었는지 여부를 나타냅니다. 만료된 자격 증명은 인증을 방해합니다.
  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  //사용자가 활성화되었는지 비활성화되었는지 여부를 나타냅니다. 비활성화된 사용자는 인증할 수 없습니다.
  @Override
  public boolean isEnabled() {
    //우리 사이트!! 1년동안 회원이 로그인을 안하면 휴먼계정으로 하기로 함.
    return user.isActivated();
  }

  @Override
  public String getName() {
    return user.getNickname();
  }
}
