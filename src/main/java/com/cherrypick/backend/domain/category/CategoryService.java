package com.cherrypick.backend.domain.category;

import static com.cherrypick.backend.domain.category.CategoryInfo.*;

import com.cherrypick.backend.domain.category.CategoryCriteria.InquiryCategoriesRequest;

public interface CategoryService {

  void registerCategory(CategoryCommand.RegisterCategory request);

  Categories inquiryCategories(InquiryCategoriesRequest request);
}
