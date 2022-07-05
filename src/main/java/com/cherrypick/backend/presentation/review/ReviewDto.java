package com.cherrypick.backend.presentation.review;

import com.cherrypick.backend.domain.review.Review.CostPerformance;
import com.cherrypick.backend.domain.review.Review.Recommendation;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class ReviewDto {

  @Getter
  @AllArgsConstructor
  public static class RegisterRequest {

    private double rating;
    @NotNull
    private Recommendation recommendation;
    @NotNull
    private CostPerformance costPerformance;
    @NotNull
    private String oneLineComment;
    @NotNull
    private String strengthComment;
    @NotNull
    private String weaknessComment;
  }
}