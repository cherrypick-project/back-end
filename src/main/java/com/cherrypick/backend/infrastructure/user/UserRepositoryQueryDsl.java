package com.cherrypick.backend.infrastructure.user;

import static com.cherrypick.backend.domain.feedback.QFeedback.feedback;
import static com.cherrypick.backend.domain.review.QReview.review;
import static com.cherrypick.backend.domain.user.QUser.user;

import com.cherrypick.backend.domain.review.Review.Status;
import com.cherrypick.backend.domain.user.QUserInfo_Percent;
import com.cherrypick.backend.domain.user.QUserInfo_ReviewCount;
import com.cherrypick.backend.domain.user.QUserInfo_Statistics;
import com.cherrypick.backend.domain.user.QUserInfo_User;
import com.cherrypick.backend.domain.user.User.Career;
import com.cherrypick.backend.domain.user.User.KnownPath;
import com.cherrypick.backend.domain.user.UserInfo;
import com.cherrypick.backend.domain.user.UserInfo.User;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.SimpleExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

@Repository
@RequiredArgsConstructor
public class UserRepositoryQueryDsl {

  private final JPAQueryFactory queryFactory;


  public Page<UserInfo.User> findByUsers(String searchName, Pageable pageable) {
    List<User> content = queryFactory.select(new QUserInfo_User(
        user.id,
        user.email,
        user.createdAt,
        new QUserInfo_ReviewCount(readyReviewCount(), approveReviewCount(), rejectReviewCount()),
        user.deleteAt
      )).from(user)
      .leftJoin(review).on(user.id.eq(review.userId))
      .where(
        searchNameEq(searchName)
      )
      .groupBy(user.id)
      .orderBy(pageable.getSort().stream().toArray(OrderSpecifier[]::new))
      .offset(pageable.getOffset())
      .limit(pageable.getPageSize())
      .fetch();

    JPAQuery<Long> countQuery = queryFactory
      .select(user.count())
      .from(user)
      .groupBy(user.id)
      .leftJoin(review).on(user.id.eq(review.userId))
      .where(
        searchNameEq(searchName)
      );

    return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
  }

  public UserInfo.Statistics findStatistics() {
    UserInfo.Statistics content = queryFactory.select(new QUserInfo_Statistics(
        user.count(),
        feedback.rating.avg(),
        feedback.count(),
        new QUserInfo_Percent(careerCount(Career.STUDENT), user.count()),
        new QUserInfo_Percent(careerCount(Career.LESS_THAN_1YEARS), user.count()),
        new QUserInfo_Percent(careerCount(Career.LESS_THAN_3YEARS), user.count()),
        new QUserInfo_Percent(careerCount(Career.LESS_THAN_6YEARS), user.count()),
        new QUserInfo_Percent(careerCount(Career.MORE_THAN_7YEARS), user.count()),
        new QUserInfo_Percent(knownPathCount(KnownPath.SEARCH), user.count()),
        new QUserInfo_Percent(knownPathCount(KnownPath.FRIEND), user.count()),
        new QUserInfo_Percent(knownPathCount(KnownPath.SNS), user.count()),
        new QUserInfo_Percent(knownPathCount(KnownPath.CAFE), user.count()),
        new QUserInfo_Percent(knownPathCount(KnownPath.BLOG), user.count()),
        new QUserInfo_Percent(knownPathCount(KnownPath.ETC), user.count())
      )).from(user)
      .leftJoin(feedback).on(user.id.eq(feedback.user.id))
      .fetchOne();

    return content;
  }

  private SimpleExpression<Integer> knownPathCount(KnownPath knownPath) {
    return Expressions.as(
      JPAExpressions.select(user.count().intValue())
        .from(user)
        .where(user.knownPath.eq(knownPath)), knownPath.toString());
  }

  private SimpleExpression<Integer> careerCount(Career career) {
    return Expressions.as(
      JPAExpressions.select(user.count().intValue())
        .from(user)
        .where(user.career.eq(career)), career.toString());
  }

  private SimpleExpression<Long> readyReviewCount() {
    return Expressions.as(
      JPAExpressions.select(review.count())
        .from(review)
        .where(review.status.eq(Status.READY)), "ready");
  }

  private SimpleExpression<Long> approveReviewCount() {
    return Expressions.as(
      JPAExpressions.select(review.count())
        .from(review)
        .where(review.status.eq(Status.APPROVE)), "approve");
  }

  private SimpleExpression<Long> rejectReviewCount() {
    return Expressions.as(
      JPAExpressions.select(review.count())
        .from(review)
        .where(review.status.eq(Status.REJECT)), "reject");
  }

  private BooleanExpression searchNameEq(String searchName) {
    if (!StringUtils.hasText(searchName)) {
      return null;
    }
    return user.nickname.like("%" + searchName + "%");
  }
}
