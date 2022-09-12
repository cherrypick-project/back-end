package com.cherrypick.backend.infrastructure.user;

import static com.cherrypick.backend.domain.review.QReview.review;
import static com.cherrypick.backend.domain.user.QUser.user;

import com.cherrypick.backend.domain.review.Review.Status;
import com.cherrypick.backend.domain.user.QUserInfo_ReviewCount;
import com.cherrypick.backend.domain.user.QUserInfo_User;
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
