package com.cherrypick.backend.presentation.category;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class CategoryDto {

  @Getter
  @AllArgsConstructor
  public static class RegisterCategory {
    @JsonProperty("name")
    private final String name;
    private final Integer depth;
    private final Long parentId;
    private final String categoryImgUrl;
  }
}
