package com.cherrypick.backend.presentation.category;

import com.cherrypick.backend.domain.category.CategoryInfo.Categories;
import com.cherrypick.backend.domain.category.CategoryInfo.Main;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
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

  @Getter
  @AllArgsConstructor
  public static class InquiryCategoriesRequest {
    private final int depth;
    private final Long parentId;
  }

  @Getter
  @AllArgsConstructor
  public static class InquiryCategoriesResponse {
    List<InquiryCategoryResponse> inquiryCategoryResponse;

    public static InquiryCategoriesResponse of(Categories categories) {
      return new InquiryCategoriesResponse(
        categories.getCategories()
          .stream()
          .map(InquiryCategoryResponse::of)
          .collect(Collectors.toList())
      );
    }
  }

  @Getter
  @AllArgsConstructor
  public static class InquiryCategoryResponse {
    private final String name;
    private final String categoryImgUrl;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public static  InquiryCategoryResponse of(Main category) {
      return new InquiryCategoryResponse(
        category.getName(),
        category.getCategoryImgUrl(),
        category.getCreatedAt(),
        category.getModifiedAt());
    }
  }
}
