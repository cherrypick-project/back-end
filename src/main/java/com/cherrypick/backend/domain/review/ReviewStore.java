package com.cherrypick.backend.domain.review;


public interface ReviewStore {

  void store(Review review);

  void approve(Long reviewId);
}