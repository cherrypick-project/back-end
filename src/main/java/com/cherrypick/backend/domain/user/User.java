package com.cherrypick.backend.domain.user;

import com.cherrypick.backend.domain.user.oauth.ProviderType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(
  access = AccessLevel.PROTECTED
)
@Builder
@AllArgsConstructor
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String providerId;
  private String email;
  private String password;
  private String nickname;
  private boolean activated;

  @Enumerated(EnumType.STRING)
  private Authority authority;

  @Enumerated(EnumType.STRING)
  private ProviderType providerType;

  private String job;

  @Enumerated(EnumType.STRING)
  private Career career;

  private String knownPath;

  public enum Career {
    STUDENT, LESS_THAN_1YEARS, LESS_THAN_3YEARS ,LESS_THAN_6YEARS, MORE_THAN_7YEARS
  }
}
