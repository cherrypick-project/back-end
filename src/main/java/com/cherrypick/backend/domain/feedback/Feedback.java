package com.cherrypick.backend.domain.feedback;

import static javax.persistence.FetchType.*;

import com.cherrypick.backend.domain.user.User;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
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
public class Feedback {

  @Id
  @Column(name = "id", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name= "user_id")
  private User user;

  private String content;

  private double rating;

  @Enumerated(EnumType.STRING)
  private Action action;

  public enum Action {
    EMAIL, CHECK
  }
}
