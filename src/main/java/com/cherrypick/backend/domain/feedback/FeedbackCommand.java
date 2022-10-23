package com.cherrypick.backend.domain.feedback;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class FeedbackCommand {

  @Getter
  @AllArgsConstructor
  public static class RegisterFeedbackRequest {

    private String content;
    private double rating;
  }
}
