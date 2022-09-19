package com.cherrypick.backend.domain.user;

import com.cherrypick.backend.domain.user.User.Career;
import com.cherrypick.backend.domain.user.User.KnownPath;
import com.cherrypick.backend.domain.user.oauth.ProviderType;
import com.querydsl.core.annotations.QueryProjection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.AllArgsConstructor;
import lombok.Getter;

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

  @Getter
  public static class Statistics {

    private long userCount;
    private double feedbackRating;
    private long feedbackCount;
    private Percent student;
    private Percent lessThan1Years;
    private Percent lessThan3Years;
    private Percent lessThan6Years;
    private Percent moreThan7Years;
    private Percent search;
    private Percent friend;
    private Percent sns;
    private Percent cafe;
    private Percent blog;
    private Percent etc;

    @QueryProjection
    public Statistics(long userCount, double feedbackRating, long feedbackCount,
      Percent student, Percent lessThan1Years, Percent lessThan3Years,
      Percent lessThan6Years, Percent moreThan7Years, Percent search, Percent friend, Percent sns,
      Percent cafe, Percent blog, Percent etc) {
      this.userCount = userCount;
      this.feedbackRating = feedbackRating;
      this.feedbackCount = feedbackCount;
      this.student = student;
      this.lessThan1Years = lessThan1Years;
      this.lessThan3Years = lessThan3Years;
      this.lessThan6Years = lessThan6Years;
      this.moreThan7Years = moreThan7Years;
      this.search = search;
      this.friend = friend;
      this.sns = sns;
      this.cafe = cafe;
      this.blog = blog;
      this.etc = etc;
    }
  }

  @Getter
  public static class Percent {

    private int percent;
    private int count;

    @QueryProjection
    public Percent(int count, long userCount) {
      System.out.println("test.." + count + ".." + userCount);
      if (count == 0 || userCount == 0) {
        this.percent = 0;
      } else {
        this.percent = (int) ((count / (double)userCount) * 100);
      }
      this.count = count;
    }
  }
}
