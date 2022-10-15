package com.cherrypick.backend.presentation.review;

import com.cherrypick.backend.application.ReviewFacade;
import com.cherrypick.backend.common.response.CommonResponse;
import com.cherrypick.backend.domain.review.ReviewInfo.Review;
import com.cherrypick.backend.presentation.review.ReviewDto.ReviewResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/v1")
@RequiredArgsConstructor
public class ReviewAdminController {

  private final ReviewFacade reviewFacade;

  @Operation(
    summary = "리뷰 목록 조회",
    responses = @ApiResponse(responseCode = "200", description = "성공",
      content = @Content(array = @ArraySchema(schema = @Schema(implementation = Review.class, description = "리뷰 정보")))
    ))
  @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
  @GetMapping("/reviews")
  public ResponseEntity<CommonResponse> inquiryReviews(
    @RequestParam(value = "userId", required = false) String userId
    , Pageable pageable
  ) {
    var response = reviewFacade.inquiryReviews(userId, pageable);
    return ResponseEntity.ok(CommonResponse.success(response));
  }

  @Operation(summary = "리뷰 승인", responses = {
    @ApiResponse(responseCode = "200", description = "성공"
    )
  })
  @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
  @PatchMapping("/reviews/{reviewId}")
  public ResponseEntity<CommonResponse> approve(
    @PathVariable Long reviewId
  ) {
    reviewFacade.approve(reviewId);
    return ResponseEntity.ok(CommonResponse.success());
  }

  @Operation(summary = "리뷰 조회", responses = {
    @ApiResponse(responseCode = "200", description = "성공",
      content = @Content(schema = @Schema(implementation = ReviewResponse.class))
    )
  })
  @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
  @GetMapping("/reviews/{reviewId}")
  public ResponseEntity<CommonResponse> inquiryReview(
    @PathVariable Long reviewId
  ) {
    var command = reviewFacade.inquiryReview(reviewId);
    var response = ReviewResponse.from(command);
    return ResponseEntity.ok(CommonResponse.success(response));
  }
}
