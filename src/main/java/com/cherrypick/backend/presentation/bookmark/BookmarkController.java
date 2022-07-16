package com.cherrypick.backend.presentation.bookmark;

import com.cherrypick.backend.application.BookmarkFacade;
import com.cherrypick.backend.common.response.CommonResponse;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/v1")
@RequiredArgsConstructor
public class BookmarkController {

  private final BookmarkFacade bookmarkFacade;

  @PreAuthorize("hasAnyRole('ROLE_ADMIN') or hasAnyRole('ROLE_MEMBERSHIP')")
  @PostMapping("/lectures/{lectureId}/bookmark")
  public ResponseEntity<CommonResponse> createBookmark(
    Principal principal,
    @PathVariable Long lectureId) {
    bookmarkFacade.createBookmark(principal.getName(), lectureId);
    return ResponseEntity.ok(CommonResponse.success());
  }
}
