package com.cherrypick.backend.application;

import com.cherrypick.backend.domain.category.CategoryCommand;
import com.cherrypick.backend.domain.category.CategoryCriteria.InquiryCategoriesRequest;
import com.cherrypick.backend.domain.category.CategoryInfo;
import com.cherrypick.backend.domain.category.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryFacade {

  private final CategoryService categoryService;

  public void registerCategory(CategoryCommand.RegisterCategory request) {
    categoryService.registerCategory(request);
  }

  public CategoryInfo.Categories inquiryCategories(InquiryCategoriesRequest request) {
    return categoryService.inquiryCategories(request);
  }
}
