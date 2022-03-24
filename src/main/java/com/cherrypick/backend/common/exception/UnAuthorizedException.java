package com.cherrypick.backend.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnAuthorizedException extends RuntimeException {

  private final ErrorCode errorCode;

  public UnAuthorizedException(ErrorCode errorCode) {
    super(errorCode.getMessage());
    this.errorCode = errorCode;
  }

  public UnAuthorizedException(String message,
      ErrorCode errorCode) {
    super(message);
    this.errorCode = errorCode;
  }
}