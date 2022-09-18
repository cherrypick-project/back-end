package com.cherrypick.backend.application;

import com.cherrypick.backend.domain.feedback.FeedbackCommand.RegisterFeedbackRequest;
import com.cherrypick.backend.domain.feedback.FeedbackInfo;
import com.cherrypick.backend.domain.feedback.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FeedbackFacade {

  private final FeedbackService feedbackService;

  public void registerFeedback(RegisterFeedbackRequest request, String name) {
    feedbackService.registerFeedback(request, name);
  }

  public Page<FeedbackInfo.Feedback> inquiryFeedbacks(Long userId, Pageable pageable) {
    return feedbackService.inquiryFeedbacks(userId, pageable);
  }
}
