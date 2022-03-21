package com.cherrypick.backend.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class OAuthProviderMissMatchException extends RuntimeException {

  public OAuthProviderMissMatchException(String message) {
    super(message);
  }
}