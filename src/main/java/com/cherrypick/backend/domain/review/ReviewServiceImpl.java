package com.cherrypick.backend.domain.review;

import static com.cherrypick.backend.domain.review.PreviewReviewIdGenerator.PREVIEW_REVIEW_COUNT;

import com.cherrypick.backend.common.exception.BusinessException;
import com.cherrypick.backend.common.exception.ErrorCode;
import com.cherrypick.backend.domain.lecture.Lecture;
import com.cherrypick.backend.domain.lecture.LectureReader;
import com.cherrypick.backend.domain.review.ReviewInfo.ReviewDetail;
import com.cherrypick.backend.domain.review.ReviewInfo.ReviewStatistics;
import com.cherrypick.backend.domain.user.User;
import com.cherrypick.backend.domain.user.UserReader;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

  private final ReviewReader reviewReader;
  private final ReviewStore reviewStore;
  private final LectureReader lectureReader;
  private final UserReader userReader;
  private final PreviewReviewIdGenerator previewReviewIdGenerator;


  @Transactional(readOnly = true)
  @Override
  public ReviewStatistics inquiryReviewStatics(Long lectureId) {
    List<ReviewInfo.ReviewDetail> reviewList = reviewReader.findAllByLectureId(lectureId);
    Reviews reviews = new Reviews(reviewList);

    return new ReviewInfo.ReviewStatistics(
      reviews.getTotalRating(),
      reviews.getCount(),
      reviews.getFrontEndRating(),
      reviews.getBackEndRating(),
      reviews.getRecommendationStatics(),
      reviews.getCostPerformanceStatics(),
      reviews.getMostViewUserGroup());
  }

  @Transactional
  @Override
  public void createReview(ReviewCommand.RegisterRequest command) {
    Long lectureId = command.getLectureId();
    String loginId = command.getLoginId();

    User user = userReader.findByProviderId(loginId).orElseThrow(
      () -> new BusinessException(loginId + "사용자를 찾지 못했습니다.", ErrorCode.NOT_FOUND_USER));
    Lecture lecture = lectureReader.findByLectureId(lectureId)
      .orElseThrow(() -> new BusinessException(lectureId + "강의를 찾지 못했습니다.",
        ErrorCode.NOT_FOUND_LECTURE));
    reviewStore.store(command.toEntity(lecture, user.getId()));
  }

  @Transactional(readOnly = true)
  @Override
  public Slice<ReviewDetail> inquiryReviews(Long lectureId, Pageable pageable, Boolean isMobile) {
    return reviewReader.findAllReviewPageableByLectureId(lectureId,
      pageable, isMobile);
  }

  @Transactional(readOnly = true)

  @Override
  public List<ReviewDetail> inquiryPreviewReviews() {
    Long maxId = reviewReader.findMaxId()
      .orElseThrow(() -> new BusinessException("리뷰를 찾지 못했습니다.", ErrorCode.NOT_FOUND_REVIEW));
    List<Long> previewReviewIds;
    if (maxId.intValue() < PREVIEW_REVIEW_COUNT) {
      previewReviewIds = previewReviewIdGenerator.createSequentialIds(maxId);
      return addReview(reviewReader.findAllPreviewReviewInIds(previewReviewIds));
    }
    previewReviewIds = previewReviewIdGenerator.createNonDuplicationRandomIds(maxId);
    return reviewReader.findAllPreviewReviewInIds(previewReviewIds);
  }

  @Transactional(readOnly = true)
  @Override
  public Page<ReviewInfo.Review> inquiryReviews(String loginId, Pageable pageable) {
     return reviewReader.findAllByLoginId(loginId,pageable);
  }

  @Transactional
  @Override
  public void approve(Long reviewId) {
    reviewStore.approve(reviewId);
  }

  private List<ReviewDetail> addReview(List<ReviewDetail> NonEnoughPreviewReviews) {
    List<ReviewDetail> previewReviews = new ArrayList<>(NonEnoughPreviewReviews);
    int reviewCount = NonEnoughPreviewReviews.size();
    int insufficientCount = PREVIEW_REVIEW_COUNT - reviewCount;
    for (int i = 0; i < insufficientCount; i++) {
      previewReviews.add(NonEnoughPreviewReviews.get(i));
    }
    return previewReviews;
  }
}
