package com.cherrypick.backend.presentation.review;

import com.cherrypick.backend.application.ReviewFacade;
import com.cherrypick.backend.common.response.CommonResponse;
import com.cherrypick.backend.domain.review.ReviewCommand;
import com.cherrypick.backend.domain.review.ReviewInfo.ReviewDetail;
import com.cherrypick.backend.presentation.review.ReviewDto.PreviewReviewResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/v1")
@RequiredArgsConstructor
public class ReviewController {

  private final ReviewFacade reviewFacade;

  @Operation(summary = "리뷰 생성", responses = {
    @ApiResponse(responseCode = "200", description = "성공"
    )
  })
  @PreAuthorize("hasAnyRole('ROLE_USER') or hasAnyRole('ROLE_ADMIN') or hasAnyRole('ROLE_MEMBERSHIP')")
  @PostMapping("/lectures/{lectureId}/review")
  public ResponseEntity<CommonResponse> createReview(
    Principal principal,
    @PathVariable Long lectureId,
    @RequestBody @Valid ReviewDto.RegisterRequest request
  ) {
    var command = new ReviewCommand.RegisterRequest(principal.getName(), lectureId, request);
    reviewFacade.createReview(command);
    return ResponseEntity.ok(CommonResponse.success());
  }

  @Operation(
    summary = "리뷰 목록 조회",
    responses = @ApiResponse(responseCode = "200", description = "성공",
      content = @Content(array = @ArraySchema(schema = @Schema(implementation = ReviewDetail.class, description = "리뷰 정보")))
    ))
  @PreAuthorize("hasAnyRole('ROLE_ADMIN') or hasAnyRole('ROLE_MEMBERSHIP')")
  @GetMapping("/lectures/{lectureId}/review")
  public ResponseEntity<CommonResponse> inquiryReviews(
    @PathVariable Long lectureId,
    Pageable pageable,
    @RequestParam(value = "isMobile", required = false, defaultValue = "false") Boolean isMobile
  ) {
    var response = reviewFacade.inquiryReviews(lectureId, pageable, isMobile);
    return ResponseEntity.ok(CommonResponse.success(response));
  }

  @Operation(
    summary = "미리보기 리뷰 조회",
    responses = @ApiResponse(responseCode = "200", description = "성공",
      content = @Content(array = @ArraySchema(schema = @Schema(implementation = PreviewReviewResponse.class, description = "리뷰 정보")))
    ))
  @GetMapping("/reviews")
  public ResponseEntity<CommonResponse> inquiryPreviewReviews() {
    var command = reviewFacade.inquiryPreviewReviews();
    var response = convertToResponse(command);
    return ResponseEntity.ok(CommonResponse.success(response));
  }

  private List<PreviewReviewResponse> convertToResponse(List<ReviewDetail> command) {
    return command
      .stream()
      .map(PreviewReviewResponse::from)
      .collect(Collectors.toList());
  }
}