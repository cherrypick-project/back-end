package com.cherrypick.backend.domain.review;

import com.cherrypick.backend.domain.review.Review.CostPerformance;
import com.cherrypick.backend.domain.review.Review.Recommendation;
import com.cherrypick.backend.domain.review.Review.Status;
import com.cherrypick.backend.domain.user.User.Career;
import com.cherrypick.backend.presentation.review.ReviewDto;
import com.cherrypick.backend.presentation.review.ReviewDto.PreviewReviewResponse;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

public class ReviewInfo {

  @Getter
  public static class ReviewDetail {

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

    @QueryProjection
    public ReviewDetail(Long id, double rating, Recommendation recommendation,
      CostPerformance costPerformance, String oneLineComment, String strengthComment,
      String weaknessComment, Status status, Long userId, String job, Career career) {
      this.id = id;
      this.rating = rating;
      this.recommendation = recommendation;
      this.costPerformance = costPerformance;
      this.oneLineComment = oneLineComment;
      this.strengthComment = strengthComment;
      this.weaknessComment = weaknessComment;
      this.status = status;
      this.userId = userId;
      this.user = new User(job, career);
    }

    public ReviewDto.PreviewReviewResponse toResponseDto() {
      return new PreviewReviewResponse(
        id,
        rating,
        recommendation,
        costPerformance,
        oneLineComment,
        strengthComment,
        weaknessComment,
        status,
        userId,
        user);
    }
  }

  @Getter
  @AllArgsConstructor
  public static class ReviewStatistics {

    private final double totalRating;
    private final long count;
    private final double frontendRating;
    private final double backendRating;
    private final RecommendationStatics recommendationStatics;
    private final CostPerformanceStatics costPerformanceStatics;
    private final MostViewUserGroup mostViewUserGroup;
  }

  @Getter
  public static class MostViewUserGroup {

    private final String job;
    private final Career career;

    public MostViewUserGroup() {
      this.job = null;
      this.career = null;
    }

    public MostViewUserGroup(User user) {
      this.job = user.getJob();
      this.career = user.getCareer();
    }
  }

  @Getter
  @AllArgsConstructor
  public static class RecommendationStatics {

    private final String good;
    private final String bad;
  }

  @Getter
  @AllArgsConstructor
  public static class CostPerformanceStatics {

    private final Long verySatisfactionCount;
    private final Long satisfactionCount;
    private final Long middleCount;
    private final Long sosoCount;
    private final String verySatisfactionPercent;
    private final String satisfactionPercent;
    private final String middlePercent;
    private final String sosoPercent;
  }

  @Getter
  @AllArgsConstructor
  @EqualsAndHashCode
  public static class User {

    private final String job;
    private final Career career;
  }
}
