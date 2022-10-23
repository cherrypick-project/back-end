package com.cherrypick.backend.infrastructure.feedback;

import com.cherrypick.backend.domain.feedback.FeedbackInfo.Feedback;
import com.cherrypick.backend.domain.feedback.FeedbackReader;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FeedbackReaderImpl implements FeedbackReader {

  private final FeedbackRepositoryQueryDsl feedbackRepositoryQueryDsl;
  private final FeedbackRepository feedbackRepository;

  @Override
  public Page<Feedback> findAllByUserId(Long userId, Pageable pageable) {
    return feedbackRepositoryQueryDsl.findAllByUserId(userId, pageable);
  }

  @Override
  public Optional<com.cherrypick.backend.domain.feedback.Feedback> findById(Long feedbackId) {
    return feedbackRepository.findById(feedbackId);
  }
}
