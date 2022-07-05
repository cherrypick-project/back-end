package com.cherrypick.backend.domain.review;

import com.cherrypick.backend.domain.lecture.Lecture;
import com.cherrypick.backend.presentation.review.ReviewDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class ReviewCommand {

  @Getter
  @AllArgsConstructor
  public static class RegisterRequest {

    private final String loginId;
    private final long lectureId;
    private final double rating;
    private final Review.Recommendation recommendation;
    private final Review.CostPerformance costPerformance;
    private final String oneLineComment;
    private final String strengthComment;
    private final String weaknessComment;

    public RegisterRequest(String loginId, Long lectureId, ReviewDto.RegisterRequest request) {
      this.loginId = loginId;
      this.lectureId = lectureId;
      this.rating = request.getRating();
      this.recommendation = request.getRecommendation();
      this.costPerformance = request.getCostPerformance();
      this.oneLineComment = request.getOneLineComment();
      this.strengthComment = request.getStrengthComment();
      this.weaknessComment = request.getWeaknessComment();
    }

    public Review toEntity(Lecture lecture, Long userId) {
      return Review.of(lecture, rating, recommendation, costPerformance, oneLineComment,
        strengthComment,
        weaknessComment, userId);
    }
  }
}