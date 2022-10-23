package com.cherrypick.backend.presentation.user;

import com.cherrypick.backend.application.UserFacade;
import com.cherrypick.backend.common.response.CommonResponse;
import com.cherrypick.backend.domain.user.UserInfo;
import com.cherrypick.backend.domain.user.UserInfo.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

  @Operation(summary = "유저 조회", responses = {
    @ApiResponse(responseCode = "200", description = "성공",
      content = @Content(schema = @Schema(implementation = User.class))
    )
  })
  @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
  @GetMapping("/users")
  public ResponseEntity<CommonResponse> inquiryUsers(
    Pageable pageable,
    String searchName
  ) {
    val userInfo = userFacade.inquiryUsers(searchName, pageable);
    return ResponseEntity.ok(CommonResponse.success(userInfo));
  }

  @Operation(summary = "유저 통계", responses = {
    @ApiResponse(responseCode = "200", description = "성공",
      content = @Content(schema = @Schema(implementation = UserInfo.Statistics.class))
    )
  })
  @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
  @GetMapping("/user/statistics")
  public ResponseEntity<CommonResponse> inquiryUserStatistics() {
    val userInfo = userFacade.inquiryStatistics();
    return ResponseEntity.ok(CommonResponse.success(userInfo));
  }
}
