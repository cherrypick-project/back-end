package com.cherrypick.backend.infrastructure.feedback;

import static com.cherrypick.backend.domain.feedback.QFeedback.feedback;
import static com.cherrypick.backend.domain.user.QUser.user;

import com.cherrypick.backend.domain.feedback.FeedbackInfo;
import com.cherrypick.backend.domain.feedback.FeedbackInfo.Feedback;
import com.cherrypick.backend.domain.feedback.QFeedbackInfo_Feedback;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class FeedbackRepositoryQueryDsl {

  private final JPAQueryFactory queryFactory;

  public Page<FeedbackInfo.Feedback> findAllByUserId(Long userId, Pageable pageable) {
    List<Feedback> content = queryFactory.select(new QFeedbackInfo_Feedback(
        feedback.id,
        user.email,
        feedback.rating,
        feedback.createdAt,
        feedback.modifiedAt,
        feedback.action))
      .from(feedback)
      .leftJoin(feedback.user, user)
      .where(userIdEq(userId))
      .orderBy(pageable.getSort().stream().toArray(OrderSpecifier[]::new))
      .offset(pageable.getOffset())
      .limit(pageable.getPageSize())
      .fetch();

    JPAQuery<Long> countQuery = queryFactory
      .select(feedback.count())
      .from(feedback)
      .leftJoin(feedback.user, user)
      .where(userIdEq(userId));

    return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
  }

  private BooleanExpression userIdEq(Long userId) {
    return userId == null ? null : user.id.eq(userId);
  }
}
