package com.cherrypick.backend.domain.feedback;

import com.cherrypick.backend.common.exception.BusinessException;
import com.cherrypick.backend.common.exception.ErrorCode;
import com.cherrypick.backend.domain.feedback.FeedbackCommand.RegisterFeedbackRequest;
import com.cherrypick.backend.domain.user.User;
import com.cherrypick.backend.domain.user.UserReader;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {

  private final UserReader userReader;
  private final FeedbackStore feedbackStore;
  private final FeedbackReader feedbackReader;

  @Override
  public void registerFeedback(RegisterFeedbackRequest command, String name) {
    User registrant = userReader.findByProviderId(name)
      .orElseThrow(() -> new BusinessException(name + " 사용자를 찾지 못했습니다.",
        ErrorCode.NOT_FOUND_USER));

    Feedback feedback = Feedback.Register(registrant, command.getContent(), command.getRating());
    feedbackStore.store(feedback);
  }

  @Override
  public Page<FeedbackInfo.Feedback> inquiryFeedbacks(Long userId, Pageable pageable) {
    return feedbackReader.findAllByUserId(userId, pageable);
  }

  @Override
  public void checkOrEmail(Long feedbackId, boolean isCheck) {
    Feedback feedback = feedbackReader.findById(feedbackId)
      .orElseThrow(() -> new BusinessException(feedbackId + "피드백을 찾지 못했습니다.",
        ErrorCode.NOT_FOUND_FEEDBACK));

    feedback.checkOrEmail(isCheck);
    feedbackStore.store(feedback);
  }
}
