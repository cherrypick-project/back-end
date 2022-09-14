package com.cherrypick.backend.presentation.review;

import com.cherrypick.backend.domain.review.Review.CostPerformance;
import com.cherrypick.backend.domain.review.Review.Recommendation;
import com.cherrypick.backend.domain.review.ReviewInfo.Review;
import com.cherrypick.backend.domain.review.ReviewInfo.ReviewDetail;
import com.cherrypick.backend.domain.review.ReviewInfo.User;
import java.time.LocalDate;
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
    private String recommendation;
    private String costPerformance;
    private String oneLineComment;
    private String strengthComment;
    private String weaknessComment;
    private String status;
    private Long userId;
    private User user;

    public PreviewReviewResponse(Long id, double rating,
      String recommendation,
      String costPerformance, String oneLineComment, String strengthComment,
      String weaknessComment, String status, Long userId,
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

    public static ReviewDto.PreviewReviewResponse from(ReviewDetail reviewDetail) {
      return new PreviewReviewResponse(
        reviewDetail.getId(),
        reviewDetail.getRating(),
        reviewDetail.getRecommendation().getDesc(),
        reviewDetail.getCostPerformance().getDesc(),
        reviewDetail.getOneLineComment(),
        reviewDetail.getStrengthComment(),
        reviewDetail.getWeaknessComment(),
        reviewDetail.getStatus().getDesc(),
        reviewDetail.getUserId(),
        reviewDetail.getUser());
    }
  }

  @Getter
  @AllArgsConstructor
  public static class ReviewResponse {

    private final Long id;
    private final String email;
    private final String lectureName;
    private final LocalDate createdAt;
    private final String status;
    private final LocalDate modifiedAt;
    private final double rating;
    private final String recommendation;
    private final String costPerformance;
    private final String oneLineComment;
    private final String strengthComment;
    private final String weaknessComment;
    private final String job;
    private final String career;

    public static ReviewResponse from(Review command) {
      ReviewDetail reviewDetail = command.getReviewDetail();
      return new ReviewResponse(
        command.getId(),
        command.getEmail(),
        command.getName(),
        command.getCreatedAt(),
        command.getStatus(),
        command.getUpdatedAt(),
        reviewDetail.getRating(),
        reviewDetail.getRecommendation().getDesc(),
        reviewDetail.getCostPerformance().getDesc(),
        reviewDetail.getOneLineComment(),
        reviewDetail.getStrengthComment(),
        reviewDetail.getWeaknessComment(),
        reviewDetail.getUser().getJob(),
        reviewDetail.getUser().getCareer().getDesc()
      );
    }
  }
}