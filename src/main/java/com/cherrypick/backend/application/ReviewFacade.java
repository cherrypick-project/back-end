package com.cherrypick.backend.application;

import com.cherrypick.backend.domain.review.ReviewCommand;
import com.cherrypick.backend.domain.review.ReviewInfo;
import com.cherrypick.backend.domain.review.ReviewInfo.Review;
import com.cherrypick.backend.domain.review.ReviewInfo.ReviewDetail;
import com.cherrypick.backend.domain.review.ReviewService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewFacade {

  private final ReviewService reviewService;

  public void createReview(ReviewCommand.RegisterRequest command) {
    reviewService.createReview(command);
  }

  public Slice<ReviewDetail> inquiryReviews(Long lectureId, Pageable pageable, Boolean isMobile) {
    return reviewService.inquiryReviews(lectureId, pageable, isMobile);
  }

  public List<ReviewDetail> inquiryPreviewReviews() {
    return reviewService.inquiryPreviewReviews();
  }

  public Page<Review> inquiryReviews(String loginId, Pageable pageable) {
    return reviewService.inquiryReviews(loginId, pageable);
  }

  public void approve(Long reviewId) {
    reviewService.approve(reviewId);
  }

  public ReviewInfo.Review inquiryReview(Long reviewId) {
    return reviewService.inquiryReview(reviewId);
  }
}