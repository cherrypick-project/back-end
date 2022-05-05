package com.cherrypick.backend.presentation.auth;

import com.cherrypick.backend.application.AuthFacade;
import com.cherrypick.backend.common.response.CommonResponse;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthFacade authFacade;
  private final AuthDtoMapper authDtoMapper;

  @PostMapping("/authenticate")
  public ResponseEntity<CommonResponse> login(
      @RequestBody @Valid AuthDto.LoginRequest request) {
    var command = authDtoMapper.of(request);
    var userInfo = authFacade.authorize(command);
    var response = authDtoMapper.of(userInfo);

    return ResponseEntity.ok(CommonResponse.success(response));
  }

  @PostMapping("/reissue")
  public ResponseEntity<CommonResponse> reissue(
      @RequestBody @Valid AuthDto.ReissueRequest request) {
    var command = authDtoMapper.of(request);
    var userInfo = authFacade.reissue(command);
    var response = authDtoMapper.of(userInfo);

    return ResponseEntity.ok(CommonResponse.success(response));
  }

  @PreAuthorize("hasAnyRole('ROLE_NEED_MORE_INFO')")
  @PatchMapping("/signup")
  public ResponseEntity<CommonResponse> signup(@AuthenticationPrincipal UserDetails user,
      @RequestBody @Valid AuthDto.SignUpRequest request) {
    request.setProviderId(user.getUsername());
    var command = authDtoMapper.of(request);
    var userInfo = authFacade.signup(command);
    var response = authDtoMapper.of(userInfo);

    return ResponseEntity.ok(CommonResponse.success(response));
  }
}
