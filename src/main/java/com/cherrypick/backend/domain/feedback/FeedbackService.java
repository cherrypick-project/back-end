package com.cherrypick.backend.domain.feedback;

import com.cherrypick.backend.domain.feedback.FeedbackCommand.RegisterFeedbackRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FeedbackService {

  void registerFeedback(RegisterFeedbackRequest feedbackCommand, String name);

  Page<FeedbackInfo.Feedback> inquiryFeedbacks(Long userId, Pageable pageable);

  void checkOrEmail(Long feedbackId, boolean isCheck);
}
