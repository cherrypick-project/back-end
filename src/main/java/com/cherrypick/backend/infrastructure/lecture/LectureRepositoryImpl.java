package com.cherrypick.backend.infrastructure.lecture;

import static com.cherrypick.backend.domain.bookmark.QBookmark.bookmark;
import static com.cherrypick.backend.domain.category.QFirstCategory.firstCategory;
import static com.cherrypick.backend.domain.category.QLectureCategory.lectureCategory;
import static com.cherrypick.backend.domain.category.QSecondCategory.secondCategory;
import static com.cherrypick.backend.domain.category.QThirdCategory.thirdCategory;
import static com.cherrypick.backend.domain.lecture.QLecture.lecture;
import static com.cherrypick.backend.domain.review.QReview.review;
import static com.cherrypick.backend.domain.user.QUser.user;

import com.cherrypick.backend.common.util.QueryDslOrderUtil;
import com.cherrypick.backend.domain.lecture.Lecture;
import com.cherrypick.backend.domain.lecture.LectureCommand.ConditionRequest;
import com.cherrypick.backend.domain.lecture.LectureInfo;
import com.cherrypick.backend.domain.lecture.LectureInfo.LectureDetail;
import com.cherrypick.backend.domain.lecture.LectureInfo.Lectures;
import com.cherrypick.backend.domain.lecture.QLectureInfo_LectureDetail;
import com.cherrypick.backend.domain.lecture.QLectureInfo_Lectures;
import com.cherrypick.backend.domain.review.Review.Status;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.SimpleExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Repository
@RequiredArgsConstructor
public class LectureRepositoryImpl {

  private final JPAQueryFactory queryFactory;

  public Page<LectureInfo.Lectures> findAllLecturePageableByLectureIdAndCategoryIdAndDepth(ConditionRequest command, Pageable pageable) {
    List<LectureInfo.Lectures> content = queryFactory.select(new QLectureInfo_Lectures(
        lecture.id,
        lecture.desktopImgUrl,
        lecture.tabletImgUrl,
        lecture.mobileImgUrl,
        lecture.name,
        lecture.isOffline,
        lecture.lectureCompany,
        lecture.lecturer,
        lecture.hashTagList,
        lecture.originLink,
        lecture.price,
        reviewCount(),
        reviewRating(),
        isBookMark(command.getProviderId())
      )).from(lecture)
      .leftJoin(bookmark).on(lecture.eq(bookmark.lecture))
      .leftJoin(bookmark.user, user)
      .where(
        searchNameEq(command.getSearchName()),
        categoryEq(command.getCategoryId(), command.getDepth())
      )
      .orderBy(
        getOrderSpecifier(pageable.getSort())
          .toArray(OrderSpecifier[]::new))
      .offset(pageable.getOffset())
      .limit(pageable.getPageSize())
      .fetch();

    JPAQuery<Long> countQuery = queryFactory
      .select(lecture.count())
      .from(lecture)
      .leftJoin(bookmark).on(lecture.eq(bookmark.lecture))
      .leftJoin(bookmark.user, user)
      .where(
        searchNameEq(command.getSearchName()),
        categoryEq(command.getCategoryId(), command.getDepth())
      );

    return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
  }

  public Slice<Lectures> findAllLectureSliceByLectureIdAndCategoryIdAndDepth(ConditionRequest command, Pageable pageable) {
    List<LectureInfo.Lectures> content = queryFactory.select(new QLectureInfo_Lectures(
        lecture.id,
        lecture.desktopImgUrl,
        lecture.tabletImgUrl,
        lecture.mobileImgUrl,
        lecture.name,
        lecture.isOffline,
        lecture.lectureCompany,
        lecture.lecturer,
        lecture.hashTagList,
        lecture.originLink,
        lecture.price,
        reviewCount(),
        reviewRating(),
        isBookMark(command.getProviderId())
      )).from(lecture)
      .leftJoin(bookmark).on(lecture.eq(bookmark.lecture))
      .leftJoin(bookmark.user, user)
      .where(
        searchNameEq(command.getSearchName()),
        categoryEq(command.getCategoryId(), command.getDepth())
      )
      .orderBy(
        getOrderSpecifier(pageable.getSort())
          .toArray(OrderSpecifier[]::new))
      .offset(pageable.getOffset())
      .limit(pageable.getPageSize())
      .fetch();

    boolean hasNext = false;
    if (content.size() > pageable.getPageSize()) {
      content.remove(pageable.getPageSize());
      hasNext = true;
    }
    return new SliceImpl<>(content,pageable,hasNext);
  }

  public LectureDetail findByLectureId(String loginId, Long lectureId) {
    return queryFactory.select(new QLectureInfo_LectureDetail(
        lecture.id,
        lecture.desktopImgUrl,
        lecture.tabletImgUrl,
        lecture.mobileImgUrl,
        lecture.name,
        lecture.isOffline,
        lecture.lectureCompany,
        lecture.lecturer,
        lecture.hashTagList,
        lecture.originLink,
        lecture.price,
        isBookMark(loginId)
      )).from(lecture)
      .leftJoin(bookmark).on(lecture.eq(bookmark.lecture))
      .leftJoin(bookmark.user, user)
      .where(
        lectureIdEq(lectureId)
      ).fetchOne();
  }

  private BooleanExpression lectureIdEq(Long lectureId) {
    return lecture.id.eq(lectureId);
  }

  private BooleanExpression isBookMark(String providerId) {
    return new CaseBuilder()
      .when(providerIdEq(providerId))
      .then(true)
      .otherwise(false);
  }

  private JPQLQuery<Double> reviewRating() {
    return JPAExpressions.select(review.rating.avg())
      .from(review)
      .where(lectureEq(), statusEq());
  }

  private SimpleExpression<Long> reviewCount() {
    return Expressions.as(
      JPAExpressions.select(review.count())
        .from(review)
        .where(lectureEq()), "reviewCount");
  }

  private BooleanExpression categoryEq(List<Long> categoryId, int depth) {
    if (CollectionUtils.isEmpty(categoryId)) {
      return null;
    }
    return lecture.id.in(
      makeSubQuery(categoryId, depth)
    );
  }

  private JPQLQuery<Long> makeSubQuery(List<Long> categoryId, int depth) {
    if (depth == 3) {
      return JPAExpressions.select(lectureCategory.id)
        .from(lectureCategory)
        .where(lectureCategory.category.id.in(categoryId));
    }
    return JPAExpressions.select(lectureCategory.id)
      .from(lectureCategory)
      .innerJoin(lectureCategory.category, thirdCategory)
      .innerJoin(thirdCategory.category, secondCategory)
      .innerJoin(secondCategory.category, firstCategory)
      .where(firstCategory.id.in(categoryId));
  }

  private BooleanExpression providerIdEq(String providerId) {
    return bookmark.user.providerId.eq(providerId);
  }

  private BooleanExpression searchNameEq(String searchName) {
    if (!StringUtils.hasText(searchName)) {
      return null;
    }
    return lectureNameEq(searchName).or(hashTagEq(searchName));
  }

  private BooleanExpression hashTagEq(String searchName) {
    return Expressions.booleanTemplate("FIND_IN_SET({0},{1})>0", searchName, lecture.hashTagList);
  }

  private BooleanExpression lectureNameEq(String searchName) {
    return lecture.name.like(searchName + "%");
  }

  private BooleanExpression lectureEq() {
    return review.lecture.eq(lecture);
  }

  private BooleanExpression statusEq() {
    return review.status.eq(Status.APPROVE);
  }

  private List<OrderSpecifier> getOrderSpecifier(Sort sort) {
    List<OrderSpecifier> orders = new ArrayList<>();
    // Sort
    sort.stream().forEach(order -> {
      Order direction = order.isAscending() ? Order.ASC : Order.DESC;
      String prop = order.getProperty();
      if (prop.equals("reviewCount")) {
        orders.add(QueryDslOrderUtil.getSortedSubQueryColumn(direction, "reviewCount"));
      } else {
        orders.add(QueryDslOrderUtil.getSoredColumn(direction, Lecture.class, prop, "lecture"));
      }
    });
    return orders;
  }
}
