package com.cherrypick.backend.presentation.feedback;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class FeedbackDto {

  @Getter
  @AllArgsConstructor
  public static class RegisterFeedbackRequest {
    private String content;
    private double rating;
  }
}
