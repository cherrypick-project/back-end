package com.cherrypick.backend.domain.review;

import static org.assertj.core.api.Assertions.assertThat;

import com.cherrypick.backend.domain.review.Review.CostPerformance;
import com.cherrypick.backend.domain.review.Review.Recommendation;
import com.cherrypick.backend.domain.review.Review.Status;
import com.cherrypick.backend.domain.review.ReviewInfo.CostPerformanceStatics;
import com.cherrypick.backend.domain.review.ReviewInfo.MostViewUserGroup;
import com.cherrypick.backend.domain.review.ReviewInfo.RecommendationStatics;
import com.cherrypick.backend.domain.review.ReviewInfo.ReviewDetail;
import com.cherrypick.backend.domain.user.User.Career;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReviewsTest {

  @DisplayName("리뷰 갯수 계산")
  @Test
  void getCount() {
    //given
    Reviews reviews = new Reviews(createReviewDetail());
    List<ReviewDetail> reviewDetails = createReviewDetail();

    //when
    long count = reviews.getCount();

    //then
    assertThat(count).isEqualTo(reviewDetails.size());
  }

  @DisplayName("총 평점을 계산")
  @Test
  void getTotalRating() {
    //given
    Reviews reviews = new Reviews(createReviewDetail());
    List<ReviewDetail> reviewDetails = createReviewDetail();

    //when
    double expected = reviewDetails.stream()
      .mapToDouble(ReviewDetail::getRating)
      .average().orElse(0.0);
    double totalRating = reviews.getTotalRating();

    //then
    assertThat(totalRating).isEqualTo(expected);
  }

  @DisplayName("프론트엔드들의 평점 계산")
  @Test
  void getFrontEndRating() {
    //given
    Reviews reviews = new Reviews(createReviewDetail());
    List<ReviewDetail> reviewDetails = createReviewDetail();

    //when
    double expected = reviewDetails.stream()
      .filter(review -> review.getUser().getJob().equals("프론트엔드"))
      .mapToDouble(ReviewInfo.ReviewDetail::getRating)
      .average().orElse(0.0);
    double frontEndRating = reviews.getFrontEndRating();

    //then
    assertThat(frontEndRating).isEqualTo(expected);
  }

  @DisplayName("백엔드들의 평점 계산")
  @Test
  void getBackEndRating() {
    //given
    Reviews reviews = new Reviews(createReviewDetail());
    List<ReviewDetail> reviewDetails = createReviewDetail();

    //when
    double expected = reviewDetails.stream()
      .filter(review -> review.getUser().getJob().equals("백엔드"))
      .mapToDouble(ReviewInfo.ReviewDetail::getRating)
      .average().orElse(0.0);
    double backEndRating = reviews.getBackEndRating();

    //then
    assertThat(backEndRating).isEqualTo(expected);
  }

  @DisplayName("가장 리뷰를 많이 남긴 직군 조회")
  @Test
  void getMostViewUserGroup() {
    //given
    Reviews reviews = new Reviews(createReviewDetail());

    //when
    MostViewUserGroup mostViewUserGroup = reviews.getMostViewUserGroup();

    //then
    assertThat(mostViewUserGroup.getCareer()).isEqualTo(Career.LESS_THAN_3YEARS);
    assertThat(mostViewUserGroup.getJob()).isEqualTo("프론트엔드");
  }

  @DisplayName("Recommendation 통계")
  @Test
  void getRecommendationStatics() {
    //given
    Reviews reviews = new Reviews(createReviewDetail());

    //when
    RecommendationStatics recommendationStatics = reviews.getRecommendationStatics();

    //then
    assertThat(recommendationStatics.getGood()).isEqualTo("50.00");
    assertThat(recommendationStatics.getBad()).isEqualTo("50.00");
  }

  @DisplayName("CostPerformance 통계")
  @Test
  void getCostPerformanceStatics() {
    //given
    Reviews reviews = new Reviews(createReviewDetail());

    //when
    CostPerformanceStatics costPerformanceStatics = reviews.getCostPerformanceStatics();

    //then
    assertThat(costPerformanceStatics.getVerySatisfactionCount()).isEqualTo(4);
    assertThat(costPerformanceStatics.getSatisfactionCount()).isEqualTo(3);
    assertThat(costPerformanceStatics.getMiddleCount()).isEqualTo(2);
    assertThat(costPerformanceStatics.getSosoCount()).isEqualTo(3);
    assertThat(costPerformanceStatics.getVerySatisfactionPercent()).isEqualTo("33.30");
    assertThat(costPerformanceStatics.getSatisfactionPercent()).isEqualTo("25.00");
    assertThat(costPerformanceStatics.getMiddlePercent()).isEqualTo("16.70");
    assertThat(costPerformanceStatics.getSosoPercent()).isEqualTo("25.00");
  }

  private List<ReviewDetail> createReviewDetail() {
    return List.of(
      new ReviewDetail(1L, 3.5, Recommendation.BAD, CostPerformance.MIDDLE, "좋아여", "굿", "마이크 소리작음",
        Status.APPROVE, 1L, "백엔드", Career.LESS_THAN_1YEARS),
      new ReviewDetail(2L, 2.5, Recommendation.GOOD, CostPerformance.VERY_SATISFACTION, "별로", "쏘쏘",
        "마이크 소리작음",
        Status.APPROVE, 2L, "프론트엔드", Career.LESS_THAN_3YEARS),
      new ReviewDetail(3L, 1.5, Recommendation.BAD, CostPerformance.SATISFACTION, "듣지마세여", "내용부실",
        "마이크 소리작음", Status.APPROVE, 1L, "백엔드", Career.LESS_THAN_3YEARS),
      new ReviewDetail(4L, 4.5, Recommendation.GOOD, CostPerformance.VERY_SATISFACTION, "좋아여", "쏘쏘",
        "마이크 소리작음",
        Status.APPROVE, 2L, "프론트엔드", Career.LESS_THAN_3YEARS),
      new ReviewDetail(5L, 3.5, Recommendation.GOOD, CostPerformance.SATISFACTION, "좋아여", "굿",
        "마이크 소리작음",
        Status.APPROVE, 2L, "백엔드", Career.LESS_THAN_1YEARS),
      new ReviewDetail(6L, 2.5, Recommendation.BAD, CostPerformance.VERY_SATISFACTION, "별로", "쏘쏘",
        "마이크 소리작음",
        Status.APPROVE, 3L, "프론트엔드", Career.LESS_THAN_3YEARS),
      new ReviewDetail(7L, 1.5, Recommendation.BAD, CostPerformance.SOSO, "듣지마세여", "내용부실",
        "마이크 소리작음", Status.APPROVE, 1L, "백엔드", Career.LESS_THAN_3YEARS),
      new ReviewDetail(8L, 4.5, Recommendation.GOOD, CostPerformance.VERY_SATISFACTION, "좋아여", "쏘쏘",
        "마이크 소리작음",
        Status.APPROVE, 3L, "프론트엔드", Career.LESS_THAN_3YEARS),
      new ReviewDetail(9L, 3.5, Recommendation.BAD, CostPerformance.MIDDLE, "좋아여", "굿", "마이크 소리작음",
        Status.APPROVE, 4L, "백엔드", Career.LESS_THAN_1YEARS),
      new ReviewDetail(10L, 2.5, Recommendation.GOOD, CostPerformance.SOSO, "별로", "쏘쏘", "마이크 소리작음",
        Status.APPROVE, 4L, "프론트엔드", Career.LESS_THAN_3YEARS),
      new ReviewDetail(11L, 1.5, Recommendation.BAD, CostPerformance.SATISFACTION, "듣지마세여", "내용부실",
        "마이크 소리작음", Status.APPROVE, 5L, "백엔드", Career.LESS_THAN_3YEARS),
      new ReviewDetail(12L, 4.5, Recommendation.GOOD, CostPerformance.SOSO, "좋아여", "쏘쏘", "마이크 소리작음",
        Status.APPROVE, 5L, "프론트엔드", Career.LESS_THAN_3YEARS)
    );
  }
}