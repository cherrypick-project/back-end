package com.cherrypick.backend.domain.feedback;

import com.cherrypick.backend.domain.feedback.Feedback.Action;
import com.querydsl.core.annotations.QueryProjection;
import java.time.LocalDateTime;
import lombok.Getter;

public class FeedbackInfo {

  @Getter
  public static class Feedback {

    private Long id;
    private String email;
    private Double rating;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String action;

    @QueryProjection
    public Feedback(Long id, String email, Double rating, LocalDateTime createdAt,
      LocalDateTime updatedAt, Action action) {
      this.id = id;
      this.email = email;
      this.rating = rating;
      this.createdAt = createdAt;
      this.updatedAt = updatedAt;
      this.action = action.toString();
    }
  }
}
