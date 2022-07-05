package com.cherrypick.backend.domain.review;

import com.cherrypick.backend.domain.review.ReviewInfo.ReviewStatistics;

public interface ReviewService {

  ReviewStatistics inquiryReviewStatics(Long lectureId);

  void createReview(ReviewCommand.RegisterRequest command);
}