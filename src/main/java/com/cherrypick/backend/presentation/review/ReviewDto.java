package com.cherrypick.backend.presentation.review;

import com.cherrypick.backend.domain.review.Review.CostPerformance;
import com.cherrypick.backend.domain.review.Review.Recommendation;
import com.cherrypick.backend.domain.review.Review.Status;
import com.cherrypick.backend.domain.review.ReviewInfo.User;
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

  @Getter
  public static class PreviewReviewResponse {
    private Long id;
    private double rating;
    private Recommendation recommendation;
    private CostPerformance costPerformance;
    private String oneLineComment;
    private String strengthComment;
    private String weaknessComment;
    private Status status;
    private Long userId;
    private User user;

    public PreviewReviewResponse(Long id, double rating,
      Recommendation recommendation,
      CostPerformance costPerformance, String oneLineComment, String strengthComment,
      String weaknessComment, Status status, Long userId,
      User user) {
      this.id = id;
      this.rating = rating;
      this.recommendation = recommendation;
      this.costPerformance = costPerformance;
      this.oneLineComment = oneLineComment;
      this.strengthComment = strengthComment;
      this.weaknessComment = weaknessComment;
      this.status = status;
      this.userId = userId;
      this.user = user;
    }
  }
}