package com.cherrypick.backend.domain.feedback;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FeedbackReader {

  Page<FeedbackInfo.Feedback> findAllByUserId(Long userId, Pageable pageable);
}
