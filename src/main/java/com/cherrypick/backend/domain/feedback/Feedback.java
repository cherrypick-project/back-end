package com.cherrypick.backend.domain.feedback;

import static javax.persistence.FetchType.LAZY;

import com.cherrypick.backend.domain.BaseEntity;
import com.cherrypick.backend.domain.user.User;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Feedback extends BaseEntity {

  @Id
  @Column(name = "id", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  private String content;

  private double rating;

  @Enumerated(EnumType.STRING)
  private Action action;

  public enum Action {
    EMAIL, CHECK
  }

  public Feedback(User user, String content, double rating) {
    this.user = user;
    this.content = content;
    this.rating = rating;
  }

  public static Feedback Register(User user, String content, double rating) {
    return new Feedback(user, content, rating);
  }
}
