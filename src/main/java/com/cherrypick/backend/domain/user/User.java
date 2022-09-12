package com.cherrypick.backend.domain.user;

import com.cherrypick.backend.domain.BaseEntity;
import com.cherrypick.backend.domain.user.UserCommand.SignUpRequest;
import com.cherrypick.backend.domain.user.oauth.ProviderType;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.time.LocalDateTime;
import java.util.Locale;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(
  access = AccessLevel.PROTECTED
)
public class User extends BaseEntity {

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
  @Enumerated(EnumType.STRING)
  private KnownPath knownPath;

  private LocalDateTime deleteAt;

  public void addUserInfo(SignUpRequest command) {

    this.authority = Authority.ROLE_USER;
    this.job = command.getJob();
    this.career = command.getCareer();
    this.knownPath = command.getKnownPath();
  }

  public void signOut() {
    this.activated = false;
  }

  public enum Career {
    STUDENT, LESS_THAN_1YEARS, LESS_THAN_3YEARS, LESS_THAN_6YEARS, MORE_THAN_7YEARS;

    @JsonCreator
    public static Career from(String s) {
      return Career.valueOf(s.toUpperCase(Locale.ROOT));
    }
  }

  public enum KnownPath {
    SEARCH, FRIEND, SNS, CAFE, BLOG, ETC;

    @JsonCreator
    public static KnownPath from(String s) {
      return KnownPath.valueOf(s.toUpperCase(Locale.ROOT));
    }
  }

  public static User OauthSignUp(String providerId, String email, String password, String name,
    ProviderType providerType) {
    return new User(providerId, email, password, name, providerType);
  }

  private User(String providerId, String email, String password, String name,
    ProviderType providerType) {
    this.providerId = providerId;
    this.email = email;
    this.password = password;
    this.nickname = name;
    this.providerType = providerType;
    this.activated = true;
    this.authority = Authority.ROLE_NEED_MORE_INFO;
  }

  private User(long id, String email) {
    this.id = id;
    this.email = email;
  }

  public static User testUser(long id, String email) {
    return new User(id,email);
  }
}
