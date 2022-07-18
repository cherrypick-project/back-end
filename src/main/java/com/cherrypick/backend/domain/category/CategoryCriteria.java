package com.cherrypick.backend.domain.category;

import com.cherrypick.backend.presentation.category.CategoryDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class CategoryCriteria {

  @Getter
  @AllArgsConstructor
  public static class InquiryCategoriesRequest {
    private final int depth;
    private final Long parentId;

    public static InquiryCategoriesRequest of(CategoryDto.InquiryCategoriesRequest request) {
      return new InquiryCategoriesRequest(request.getDepth(), request.getParentId());
    }
  }
}
