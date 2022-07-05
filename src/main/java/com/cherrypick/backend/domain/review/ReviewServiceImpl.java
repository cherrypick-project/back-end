package com.cherrypick.backend.domain.review;

import com.cherrypick.backend.common.exception.BusinessException;
import com.cherrypick.backend.common.exception.ErrorCode;
import com.cherrypick.backend.domain.lecture.Lecture;
import com.cherrypick.backend.domain.lecture.LectureReader;
import com.cherrypick.backend.domain.review.ReviewInfo.ReviewStatistics;
import com.cherrypick.backend.domain.user.User;
import com.cherrypick.backend.domain.user.UserReader;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

  private final ReviewReader reviewReader;
  private final ReviewStore reviewStore;
  private final LectureReader lectureReader;
  private final UserReader userReader;


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

  @Override
  public void createReview(ReviewCommand.RegisterRequest command) {
    long lectureId = command.getLectureId();
    String loginId = command.getLoginId();

    User user = userReader.findByProviderId(loginId).orElseThrow(
      () -> new BusinessException(loginId + "사용자를 찾지 못했습니다.", ErrorCode.NOT_FOUND_USER));
    Lecture lecture = lectureReader.findByLectureId(lectureId)
      .orElseThrow(() -> new BusinessException(lectureId + "강의를 찾지 못했습니다.",
        ErrorCode.NOT_FOUND_LECTURE));
    reviewStore.store(command.toEntity(lecture, user.getId()));
  }
}
