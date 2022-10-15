package com.cherrypick.backend.presentation.feedback;

import com.cherrypick.backend.application.FeedbackFacade;
import com.cherrypick.backend.common.response.CommonResponse;
import com.cherrypick.backend.domain.feedback.FeedbackInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FeedbackAdminController {

  private final FeedbackFacade feedbackFacade;

  @Operation(
    summary = "피드백 목록 조회",
    responses = @ApiResponse(responseCode = "200", description = "성공",
      content = @Content(array = @ArraySchema(schema = @Schema(implementation = FeedbackInfo.Feedback.class, description = "피드백 정보")))
    ))
  @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
  @GetMapping("/admin/v1/feedbacks")
  public ResponseEntity<CommonResponse> inquiryFeedbacks(Pageable pageable, Long userId) {
    val response = feedbackFacade.inquiryFeedbacks(userId, pageable);

    return ResponseEntity.ok(CommonResponse.success(response));
  }

  @Operation(
    summary = "피드백 확인하기",
    responses = @ApiResponse(responseCode = "200", description = "성공"
    ))
  @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
  @PatchMapping("/admin/v1/feedbacks/{feedbackId}")
  public ResponseEntity<CommonResponse> checkOrEmail(@PathVariable Long feedbackId,
    boolean isCheck) {
    feedbackFacade.checkOrEmail(feedbackId, isCheck);

    return ResponseEntity.ok(CommonResponse.success());
  }
}
