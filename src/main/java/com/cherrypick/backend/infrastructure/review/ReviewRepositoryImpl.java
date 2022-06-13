package com.cherrypick.backend.infrastructure.review;

import static com.cherrypick.backend.domain.review.QReview.review;
import static com.cherrypick.backend.domain.user.QUser.user;

import com.cherrypick.backend.domain.review.QReviewInfo_CostPerformanceStatistics;
import com.cherrypick.backend.domain.review.QReviewInfo_RatingByJob;
import com.cherrypick.backend.domain.review.QReviewInfo_RecommendationStatistics;
import com.cherrypick.backend.domain.review.ReviewInfo.CostPerformanceStatistics;
import com.cherrypick.backend.domain.review.ReviewInfo.RatingByJob;
import com.cherrypick.backend.domain.review.ReviewInfo.RecommendationStatistics;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ReviewRepositoryImpl {

  private final JPAQueryFactory queryFactory;


  public List<RatingByJob> findRatingByJobByLectureId(Long lectureId) {
    return queryFactory.select(new QReviewInfo_RatingByJob(
        user.job,
        user.career,
        review.rating.avg(),
        review.id.count()
      )).from(review)
      .innerJoin(user).on(user.id.eq(review.userId))
      .where(lectureIdEq(lectureId))
      .groupBy(user.job, user.career)
      .fetch();
  }

  public List<RecommendationStatistics> findRecommendationStatisticsByLectureId(Long lectureId) {
    return queryFactory.select(
        new QReviewInfo_RecommendationStatistics(
          review.recommendation,
          review.count()
        )).from(review)
      .where(lectureIdEq(lectureId))
      .groupBy(review.recommendation)
      .fetch();
  }

  public List<CostPerformanceStatistics> findCostPerformanceStatisticsByLectureId(Long lectureId) {
    return queryFactory.select(
        new QReviewInfo_CostPerformanceStatistics(
          review.costPerformance,
          review.count()
        )).from(review)
      .where(lectureIdEq(lectureId))
      .groupBy(review.costPerformance)
      .fetch();
  }

  private BooleanExpression lectureIdEq(Long lectureId) {
    return review.lecture.id.eq(lectureId);
  }
}
