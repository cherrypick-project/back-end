package com.cherrypick.backend.infrastructure.review;

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
}
