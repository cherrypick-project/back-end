package com.cherrypick.backend.presentation.review;

import com.cherrypick.backend.application.ReviewFacade;
import com.cherrypick.backend.common.response.CommonResponse;
import com.cherrypick.backend.domain.review.ReviewCommand;
import java.security.Principal;
import java.util.Optional;
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

  @PreAuthorize("hasAnyRole('ROLE_ADMIN') or hasAnyRole('ROLE_MEMBERSHIP')")
  @GetMapping("/lectures/{lectureId}/review")
  public ResponseEntity<CommonResponse> inquiryReviews(
    @PathVariable Long lectureId,
    Pageable pageable,
    @RequestParam(value = "isMobile", required = false, defaultValue = "false") Boolean isMobile
  ) {
    isMobile = Optional.ofNullable(isMobile).orElse(false);
    if (isMobile) {
      var response = reviewFacade.inquiryReviewsForMobile(lectureId, pageable);
      return ResponseEntity.ok(CommonResponse.success(response));
    }
    var response = reviewFacade.inquiryReviews(lectureId, pageable);
    return ResponseEntity.ok(CommonResponse.success(response));
  }

  @GetMapping("/reviews")
  public ResponseEntity<CommonResponse> inquiryPreviewReviews() {
    return ResponseEntity.ok(CommonResponse.success(reviewFacade.inquiryPreviewReviews()));
  }
}