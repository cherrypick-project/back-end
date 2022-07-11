package com.cherrypick.backend.presentation.review;

import com.cherrypick.backend.application.ReviewFacade;
import com.cherrypick.backend.common.response.CommonResponse;
import com.cherrypick.backend.domain.review.ReviewCommand;
import java.security.Principal;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/v1")
@RequiredArgsConstructor
public class ReviewController {

  private final ReviewFacade reviewFacade;

  @PreAuthorize("hasAnyRole('ROLE_USER') or hasAnyRole('ROLE_ADMIN') or hasAnyRole('ROLE_MEMBERSHIP')")
  @PostMapping("/lectures/{lectureId}/review")
  public ResponseEntity<CommonResponse> createReview(Principal principal,
    @PathVariable Long lectureId, @RequestBody @Valid ReviewDto.RegisterRequest request) {
    var command = new ReviewCommand.RegisterRequest(principal.getName(), lectureId, request);
    reviewFacade.createReview(command);
    return ResponseEntity.ok(CommonResponse.success());
  }
}