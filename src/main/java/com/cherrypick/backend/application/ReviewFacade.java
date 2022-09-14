package com.cherrypick.backend.application;

import com.cherrypick.backend.domain.review.ReviewCommand;
import com.cherrypick.backend.domain.review.ReviewInfo.ReviewDetail;
import com.cherrypick.backend.domain.review.ReviewService;
import com.cherrypick.backend.presentation.review.ReviewDto.PreviewReviewResponse;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
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

  public List<PreviewReviewResponse> inquiryPreviewReviews() {
    return reviewService.inquiryPreviewReviews()
      .stream()
      .map(ReviewDetail::toResponseDto)
      .collect(Collectors.toList());
  }
}