package com.cherrypick.backend.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
  // Common
  INVALID_INPUT_VALUE(400, "유효하지 않는 입력값 입니다."),
  METHOD_NOT_ALLOWED(400, "지원하지 않는 Method 입니다."),
  UNAUTHORIZED(401, "인증에 실패하였습니다."),
  ACCESS_DENIED(403, "요청한 리소스에 대한 접근권한이 없습니다."),
  URL_NOT_FOUND(404, "요청하신 url을 찾지 못했습니다."),
  INTERNAL_SERVER_ERROR(500, "Internal Server Error"),

  // User
  NOT_FOUND_USER(200, "사용자를 찾지 못하였습니다."),
  NOT_ACTIVE_ACCOUNT(200, "비활성 계정입니다."),

  // Lecture
  NOT_FOUND_LECTURE(200, "강의를 찾지 못하였습니다."),
  NOT_FOUND_REVIEW(200, "리뷰를 찾지 못했습니다." );

  private final String message;
  private final int status;


  ErrorCode(final int status, final String message) {
    this.status = status;
    this.message = message;
  }
}
