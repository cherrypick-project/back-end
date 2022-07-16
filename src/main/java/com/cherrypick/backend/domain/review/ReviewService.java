package com.cherrypick.backend.domain.review;

import com.cherrypick.backend.domain.review.ReviewInfo.ReviewDetail;
import com.cherrypick.backend.domain.review.ReviewInfo.ReviewStatistics;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface ReviewService {

  ReviewStatistics inquiryReviewStatics(Long lectureId);

  void createReview(ReviewCommand.RegisterRequest command);

  Page<ReviewDetail> inquiryReviews(Long lectureId, Pageable pageable);

  Slice<ReviewDetail> inquiryReviewsForMobile(Long lectureId, Pageable pageable);

  List<ReviewDetail> inquiryPreviewReviews();
}