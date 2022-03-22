package com.cherrypick.backend.presentation.auth;

import com.cherrypick.backend.application.AuthFacade;
import com.cherrypick.backend.presentation.auth.AuthDto.LoginResponse;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
  public ResponseEntity<LoginResponse> login(@RequestBody @Valid AuthDto.LoginRequest request) {
    var command = authDtoMapper.of(request);
    var userInfo = authFacade.authorize(command);
    var response = authDtoMapper.of(userInfo);

    return ResponseEntity.ok(response);
  }
}
