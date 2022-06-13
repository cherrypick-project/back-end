package com.cherrypick.backend.domain.review;

import com.cherrypick.backend.domain.review.ReviewInfo.Statistics;

public interface ReviewService {

  Statistics inquiryReviewStatics(Long lectureId);
}
