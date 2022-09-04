package com.cherrypick.backend.presentation.review;

import com.cherrypick.backend.application.ReviewFacade;
import com.cherrypick.backend.common.response.CommonResponse;
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

  @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
  @GetMapping("/reviews")
  public ResponseEntity<CommonResponse> inquiryReviews(
    @RequestParam(value = "userId", required = false) String userId
    , Pageable pageable
  ) {
    var response = reviewFacade.inquiryReviews(userId, pageable);
    return ResponseEntity.ok(CommonResponse.success(response));
  }

  @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
  @PatchMapping("/reviews/{reviewId}")
  public ResponseEntity<CommonResponse> approve(
    @PathVariable Long reviewId
  ) {
    reviewFacade.approve(reviewId);
    return ResponseEntity.ok(CommonResponse.success());
  }

}
