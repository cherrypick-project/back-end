package com.cherrypick.backend.presentation.user;

import com.cherrypick.backend.application.UserFacade;
import com.cherrypick.backend.common.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/v1/")
@RequiredArgsConstructor
public class UserAdminController {

  private final UserFacade userFacade;

  @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
  @GetMapping("/users")
  public ResponseEntity<CommonResponse> inquiryUsers(
    Pageable pageable,
    String searchName
  ) {
    val userInfo = userFacade.inquiryUsers(searchName, pageable);
    return ResponseEntity.ok(CommonResponse.success(userInfo));
  }
  
  @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
  @GetMapping("/user/statistics")
  public ResponseEntity<CommonResponse> inquiryUserStatistics() {
    val userInfo = userFacade.inquiryStatistics();
    return ResponseEntity.ok(CommonResponse.success(userInfo));
  }
}
