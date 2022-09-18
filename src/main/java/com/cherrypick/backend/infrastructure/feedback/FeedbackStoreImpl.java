package com.cherrypick.backend.infrastructure.feedback;

import com.cherrypick.backend.domain.feedback.Feedback;
import com.cherrypick.backend.domain.feedback.FeedbackStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FeedbackStoreImpl implements FeedbackStore {

  private final FeedbackRepository feedbackRepository;

  @Override
  public void store(Feedback feedback) {
    feedbackRepository.save(feedback);
  }
}
