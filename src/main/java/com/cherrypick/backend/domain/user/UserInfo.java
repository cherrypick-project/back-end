package com.cherrypick.backend.domain.user;

import com.cherrypick.backend.domain.user.User.Career;
import com.cherrypick.backend.domain.user.User.KnownPath;
import com.cherrypick.backend.domain.user.oauth.ProviderType;
import com.querydsl.core.annotations.QueryProjection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.format.datetime.DateFormatter;

public class UserInfo {

  @Getter
  @AllArgsConstructor
  public static class Token {

    private String accessToken;
    private String refreshToken;
  }

  @Getter
  @AllArgsConstructor
  public static class Profile {

    private String email;
    private String nickname;
    private boolean activated;
    private Authority authority;
    private ProviderType providerType;
    private String job;
    private Career career;
    private KnownPath knownPath;

  }

  @Getter
  @AllArgsConstructor
  public static class SignOut {

    private String email;
  }

  @Getter
  public static class User {

    private Long id;
    private String email;
    private String createdAt;
    private ReviewCount review;
    private String deleteAt;

    @QueryProjection
    public User(Long id, String email, LocalDateTime createdAt, ReviewCount review,
      LocalDateTime deleteAt) {
      DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy.MM.dd");

      this.id = id;
      this.email = email;
      this.review = review;
      if (createdAt == null) {
        this.createdAt = null;
      } else {
        this.createdAt = createdAt.format(pattern);
      }

      if (deleteAt == null) {
        this.deleteAt = null;
      } else {
        this.deleteAt = deleteAt.format(pattern);
      }
    }
  }

  @Getter
  public static class ReviewCount {

    private Long ready;
    private Long approve;
    private Long reject;

    @QueryProjection
    public ReviewCount(Long ready, Long approve, Long reject) {
      this.ready = ready;
      this.approve = approve;
      this.reject = reject;
    }
  }
}
