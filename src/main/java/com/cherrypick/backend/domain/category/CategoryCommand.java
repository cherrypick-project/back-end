package com.cherrypick.backend.domain.category;

import com.cherrypick.backend.presentation.category.CategoryDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class CategoryCommand {

  @Getter
  @AllArgsConstructor
  public static class RegisterCategory {
    private final String name;
    private final int depth;
    private final Long parentId;
    private final String categoryImgUrl;

    public static RegisterCategory of(CategoryDto.RegisterCategory request) {
      return new RegisterCategory(request.getName(),
        request.getDepth(),
        request.getParentId(),
        request.getCategoryImgUrl());
    }
  }
}
