package com.cherrypick.backend.infrastructure.review;

import com.cherrypick.backend.common.exception.BusinessException;
import com.cherrypick.backend.common.exception.ErrorCode;
import com.cherrypick.backend.domain.review.Review;
import com.cherrypick.backend.domain.review.ReviewStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReviewStoreImpl implements ReviewStore {

  private final ReviewRepository reviewRepository;

  @Override
  public void store(Review review) {
    reviewRepository.save(review);
  }

  @Override
  public void approve(Long reviewId) {
    Review review = reviewRepository.findById(reviewId)
      .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_LECTURE));

    review.approve();
  }
}
