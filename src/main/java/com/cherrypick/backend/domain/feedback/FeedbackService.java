package com.cherrypick.backend.domain.feedback;

import com.cherrypick.backend.domain.feedback.FeedbackCommand.RegisterFeedbackRequest;

public interface FeedbackService {

  void registerFeedback(RegisterFeedbackRequest feedbackCommand, String name);
}
