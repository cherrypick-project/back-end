package com.cherrypick.backend.infrastructure.review;

import static com.cherrypick.backend.domain.lecture.QLecture.lecture;
import static com.cherrypick.backend.domain.review.QReview.review;
import static com.cherrypick.backend.domain.user.QUser.user;

import com.cherrypick.backend.common.util.QueryDslOrderUtil;
import com.cherrypick.backend.domain.review.QReviewInfo_Review;
import com.cherrypick.backend.domain.review.QReviewInfo_ReviewDetail;
import com.cherrypick.backend.domain.review.Review;
import com.cherrypick.backend.domain.review.ReviewInfo;
import com.cherrypick.backend.domain.review.ReviewInfo.ReviewDetail;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

@Repository
@RequiredArgsConstructor
public class ReviewRepositoryQueryDsl {

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

  public Slice<ReviewDetail> findAllReviewPageableByLectureId(Long lectureId,
    Pageable pageable, Boolean isMobile) {
    List<ReviewDetail> content = queryFactory.select(new QReviewInfo_ReviewDetail(
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
      .orderBy(
        getOrderSpecifier(pageable.getSort())
          .toArray(OrderSpecifier[]::new))
      .offset(pageable.getOffset())
      .limit(pageable.getPageSize())
      .fetch();

    if (isMobile) {
      boolean hasNext = false;
      if (content.size() > pageable.getPageSize()) {
        content.remove(pageable.getPageSize());
        hasNext = true;
      }
      return new SliceImpl<>(content, pageable, hasNext);
    }

    JPAQuery<Long> countQuery = queryFactory
      .select(lecture.count())
      .from(review)
      .innerJoin(user).on(user.id.eq(review.userId))
      .where(lectureIdEq(lectureId));

    return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
  }

  private List<OrderSpecifier> getOrderSpecifier(Sort sort) {
    List<OrderSpecifier> orders = new ArrayList<>();
    // Sort
    sort.stream().forEach(order -> {
      Order direction = order.isAscending() ? Order.ASC : Order.DESC;
      String prop = order.getProperty();
      orders.add(QueryDslOrderUtil.getSoredColumn(direction, Review.class, prop, "review"));
    });
    return orders;
  }

  public Optional<Long> findMaxId() {
    Long maxId = queryFactory.select(review.id.max())
      .from(review)
      .fetchOne();

    return Optional.ofNullable(maxId);
  }

  public List<ReviewDetail> findAllPreviewReviewInIds(List<Long> previewReviewIds) {
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
      .where(lectureInIdEq(previewReviewIds))
      .fetch();
  }

  private BooleanExpression lectureInIdEq(List<Long> previewReviewIds) {
    return review.id.in(previewReviewIds);
  }

  public Page<ReviewInfo.Review> findAllByLoginId(String loginId, Pageable pageable) {
    List<ReviewInfo.Review> content = queryFactory.select(new QReviewInfo_Review(
        review.id,
        user.email,
        lecture.name,
        review.createdAt,
        review.status,
        review.modifiedAt
      ))
      .from(review)
      .innerJoin(user).on(user.id.eq(review.userId))
      .innerJoin(lecture).on(review.lecture.id.eq(lecture.id))
      .where(emailEq(loginId))
      .orderBy(
        getOrderSpecifier(pageable.getSort())
          .toArray(OrderSpecifier[]::new))
      .offset(pageable.getOffset())
      .limit(pageable.getPageSize())
      .fetch();

    JPAQuery<Long> countQuery = queryFactory
      .select(review.count())
      .from(review)
      .innerJoin(user).on(user.id.eq(review.userId))
      .where(emailEq(loginId));

    return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
  }

  private static BooleanExpression emailEq(String loginId) {
    if (StringUtils.hasText(loginId)) {
      return user.email.eq(loginId);
    }
    return null;
  }

  public ReviewDetail findReviewDetailById(Long reviewId) {
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
      .where(reviewIdEq(reviewId))
      .fetchFirst();
  }

  private BooleanExpression reviewIdEq(Long reviewId) {
    return review.id.eq(reviewId);
  }

  public ReviewInfo.Review findReviewById(Long reviewId) {
    return queryFactory.select(new QReviewInfo_Review(
        review.id,
        user.email,
        lecture.name,
        review.createdAt,
        review.status,
        review.modifiedAt
      ))
      .from(review)
      .innerJoin(user).on(user.id.eq(review.userId))
      .innerJoin(lecture).on(review.lecture.id.eq(lecture.id))
      .where(reviewIdEq(reviewId))
      .fetchFirst();
  }
}
