package com.cherrypick.backend.domain.user;

import com.cherrypick.backend.domain.user.UserCommand.SignUpRequest;
import com.cherrypick.backend.domain.user.oauth.ProviderType;
import com.fasterxml.jackson.annotation.JsonCreator;
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
  @Enumerated(EnumType.STRING)
  private KnownPath knownPath;

  public void addUserInfo(SignUpRequest command) {

    this.authority = Authority.ROLE_USER;
    this.job = command.getJob();
    this.career = command.getCareer();
    this.knownPath = command.getKnownPath();
  }

  public void signOut() {
    this.activated = false;
  }

  @Getter
  public enum Career {
    STUDENT("STUDENT", "학생"),
    LESS_THAN_1YEARS("LESS_THAN_1YEARS", "1년 미만"),
    LESS_THAN_3YEARS("LESS_THAN_3YEARS", "1~3년차"),
    LESS_THAN_6YEARS("LESS_THAN_6YEARS", "3~6년차"),
    MORE_THAN_7YEARS("MORE_THAN_7YEARS", "7년차 이상");

    private final String code;
    private final String desc;

    Career(String code, String desc) {
      this.code = code;
      this.desc = desc;
    }

    @JsonCreator
    public static Career from(String s) {
      return Career.valueOf(s.toUpperCase(Locale.ROOT));
    }
  }

  @Getter
  public enum KnownPath {
    SEARCH("SEARCH", "검색"),
    FRIEND("FRIEND", "지인"),
    SNS("SNS", "SNS"),
    CAFE("CAFE", "카페"),
    BLOG("BLOG", "블로그"),
    ETC("ETC", "기타");

    private final String code;
    private final String desc;

    KnownPath(String code, String desc) {
      this.code = code;
      this.desc = desc;
    }

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
    return new User(id, email);
  }
}
