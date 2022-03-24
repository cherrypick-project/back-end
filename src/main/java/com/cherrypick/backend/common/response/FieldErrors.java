package com.cherrypick.backend.common.response;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.BindingResult;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FieldErrors {

  private List<FieldError> errors;

  public FieldErrors(
      List<FieldError> errors) {
    this.errors = errors;
  }

  public static FieldErrors of(BindingResult bindingResult) {
    return bindingResult.getFieldErrors()
        .stream()
        .map(FieldError::of)
        .collect(Collectors.collectingAndThen(toList(), FieldErrors::new));
  }

  public static class FieldError {

    private final String field;
    private final String value;
    private final String reason;

    public FieldError(String field, String value, String reason) {
      this.field = field;
      this.value = value;
      this.reason = reason;
    }

    public static FieldError of(org.springframework.validation.FieldError fieldError) {
      return new FieldError(fieldError.getField(), (String) fieldError.getRejectedValue(),
          fieldError.getDefaultMessage());
    }
  }
}
