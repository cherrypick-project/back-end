package com.cherrypick.backend.presentation.auth;

import com.cherrypick.backend.common.jwt.JwtFilter;
import com.cherrypick.backend.common.jwt.TokenProvider;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

  private final TokenProvider tokenProvider;
  private final AuthenticationManagerBuilder authenticationManagerBuilder;

  @PostMapping("/login")
  public ResponseEntity<AuthDto.LoginResponse> authorize(
    @Valid @RequestBody AuthDto.LoginRequest request) {

    UsernamePasswordAuthenticationToken authenticationToken =
      new UsernamePasswordAuthenticationToken(request.getProviderId(), request.getPassword());

    /*
     authenticate메소드가 실행이 될 때
     CustomUserDetailsService의 loadUserByUsername메소드가 실행된다.
     */
    Authentication authentication = authenticationManagerBuilder.getObject()
      .authenticate(authenticationToken);
    SecurityContextHolder.getContext()
      .setAuthentication(authentication); // Authentication객체를 SecurityContext에 저장

    String jwt = tokenProvider.createToken(authentication);

    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
    return new ResponseEntity<>(new AuthDto.LoginResponse(jwt), httpHeaders, HttpStatus.OK);
  }
}
