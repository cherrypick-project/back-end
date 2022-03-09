package com.cherrypick.backend.presentation.auth;

import com.cherrypick.backend.application.AuthFacade;
import com.cherrypick.backend.common.jwt.JwtFilter;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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

  @PostMapping("/login")
  public ResponseEntity<AuthDto.LoginResponse> authorize(
    @Valid @RequestBody AuthDto.LoginRequest request) {

    var userCommand = authDtoMapper.of(request);
    System.out.println(userCommand.getProviderId() +"test...");
    var token = authFacade.login(userCommand);
    var response = new AuthDto.LoginResponse(token);

    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + token);

    return new ResponseEntity<>(response, httpHeaders, HttpStatus.OK);
  }
}
