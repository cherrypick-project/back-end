package com.cherrypick.backend.common.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommonResponse<T> {

  private Result result;
  private T data;
  private String message;

  //status 200 + success (message가 있을 경우)
  public static <T> CommonResponse success(T data, String message) {
    return CommonResponse.builder()
      .result(Result.SUCCESS)
      .data(data)
      .message(message)
      .build();
  }

  //status 200 + success (message가 없을 경우)
  public static <T> CommonResponse success(T data) {
    return CommonResponse.builder()
      .result(Result.SUCCESS)
      .data(data)
      .build();
  }

  //status 200 + success (data가 없을 경우)
  public static <T> CommonResponse success() {
    return CommonResponse.builder()
      .result(Result.SUCCESS)
      .build();
  }

  //status 200 + fail
  public static <T> CommonResponse fail(T data, String message) {
    return CommonResponse.builder()
      .result(Result.FAIL)
      .data(data)
      .message(message)
      .build();
  }

  public static <T> CommonResponse fail(String message) {
    return CommonResponse.builder()
      .result(Result.FAIL)
      .message(message)
      .build();
  }

  public enum Result {
    SUCCESS, FAIL
  }
}
