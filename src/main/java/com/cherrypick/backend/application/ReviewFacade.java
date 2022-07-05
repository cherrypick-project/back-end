package com.cherrypick.backend.application;

import com.cherrypick.backend.domain.review.ReviewCommand;
import com.cherrypick.backend.domain.review.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewFacade {

  private final ReviewService reviewService;

  public void createReview(ReviewCommand.RegisterRequest command) {
    reviewService.createReview(command);
  }
}