package com.cherrypick.backend.presentation.user;

import com.cherrypick.backend.application.UserFacade;
import com.cherrypick.backend.common.response.CommonResponse;
import com.cherrypick.backend.domain.user.UserInfo.Profile;
import com.cherrypick.backend.presentation.user.UserDto.ProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
public class UserController {

  private final UserFacade userFacade;
  private final UserDtoMapper userDtoMapper;

  @PreAuthorize("hasAnyRole('ROLE_USER') or hasAnyRole('ROLE_ADMIN') or hasAnyRole('ROLE_MEMBERSHIP')")
  @GetMapping("/user")
  public ResponseEntity<CommonResponse> searchUserProfile(@AuthenticationPrincipal UserDetails userDetails) {
    var loginId = userDetails.getUsername();
    var profile = userFacade.searchUserProfile(loginId);
    var response = userDtoMapper.of(profile);
    return ResponseEntity.ok(CommonResponse.success(response));
  }
}
