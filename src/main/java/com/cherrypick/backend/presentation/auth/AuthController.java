package com.cherrypick.backend.presentation.auth;

import com.cherrypick.backend.application.AuthFacade;
import com.cherrypick.backend.common.response.CommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
@RequestMapping("/user/v1/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthFacade authFacade;
  private final AuthDtoMapper authDtoMapper;

  @Operation(summary = "로그인", responses = {
    @ApiResponse(responseCode = "200", description = "성공",
      content = @Content(schema = @Schema(implementation = AuthDto.LoginResponse.class))
    )
  })
  @PostMapping("/authenticate")
  public ResponseEntity<CommonResponse> login(
    @RequestBody @Valid AuthDto.LoginRequest request) {
    var command = authDtoMapper.of(request);
    var userInfo = authFacade.authorize(command);
    var response = authDtoMapper.of(userInfo);

    return ResponseEntity.ok(CommonResponse.success(response));
  }

  @Operation(summary = "리프레시 토큰 재발급", responses = {
    @ApiResponse(responseCode = "200", description = "성공",
      content = @Content(schema = @Schema(implementation = AuthDto.LoginResponse.class))
    )
  })
  @PostMapping("/reissue")
  public ResponseEntity<CommonResponse> reissue(
    @RequestBody @Valid AuthDto.ReissueRequest request) {
    var command = authDtoMapper.of(request);
    var userInfo = authFacade.reissue(command);
    var response = authDtoMapper.of(userInfo);

    return ResponseEntity.ok(CommonResponse.success(response));
  }

  @Operation(summary = "회원가입", responses = {
    @ApiResponse(responseCode = "200", description = "성공",
      content = @Content(schema = @Schema(implementation = AuthDto.LoginResponse.class))
    )
  })
  @PreAuthorize("hasAnyRole('ROLE_NEED_MORE_INFO')")
  @PatchMapping("/sign-up")
  public ResponseEntity<CommonResponse> signUp(@AuthenticationPrincipal UserDetails user,
    @RequestBody @Valid AuthDto.SignUpRequest request) {
    request.setProviderId(user.getUsername());
    var command = authDtoMapper.of(request);
    var userInfo = authFacade.signUp(command);
    var response = authDtoMapper.of(userInfo);

    return ResponseEntity.ok(CommonResponse.success(response));
  }
}
