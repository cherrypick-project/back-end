package com.cherrypick.backend.infrastructure.review;

import static com.cherrypick.backend.domain.review.QReview.review;
import static com.cherrypick.backend.domain.user.QUser.user;

import com.cherrypick.backend.domain.review.QReviewInfo_ReviewDetail;
import com.cherrypick.backend.domain.review.ReviewInfo.ReviewDetail;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ReviewRepositoryImpl {

  private final JPAQueryFactory queryFactory;

  public List<ReviewDetail> findAllByLectureId(Long lectureId) {
    return queryFactory.select(new QReviewInfo_ReviewDetail(
        review.id,
        review.rating,
        review.recommendation,
        review.costPerformance,
        review.oneLineComment,
        review.strengthComment,
        review.weaknessComment,
        review.status,
        user.id,
        user.job,
        user.career
      )).from(review)
      .innerJoin(user).on(user.id.eq(review.userId))
      .where(lectureIdEq(lectureId))
      .fetch();
  }

  private BooleanExpression lectureIdEq(Long lectureId) {
    return review.lecture.id.eq(lectureId);
  }
}
