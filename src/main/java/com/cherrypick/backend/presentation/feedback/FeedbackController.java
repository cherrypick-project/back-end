package com.cherrypick.backend.presentation.feedback;

import com.cherrypick.backend.application.FeedbackFacade;
import com.cherrypick.backend.common.response.CommonResponse;
import com.cherrypick.backend.presentation.feedback.FeedbackDto.RegisterFeedbackRequest;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/v1/")
@RequiredArgsConstructor
public class FeedbackController {

  private final FeedbackFacade feedbackFacade;
  private final FeedbackDtoMapper feedbackDtoMapper;

  @PreAuthorize("hasAnyRole('ROLE_NEED_MORE_INFO') or hasAnyRole('ROLE_USER')")
  @PostMapping("/feedback")
  public ResponseEntity<CommonResponse> registerFeedback(
    @RequestBody RegisterFeedbackRequest request,
    Principal principal) {
    val command = feedbackDtoMapper.of(request);
    feedbackFacade.registerFeedback(command, principal.getName());
    return ResponseEntity.ok(CommonResponse.success());
  }
}
