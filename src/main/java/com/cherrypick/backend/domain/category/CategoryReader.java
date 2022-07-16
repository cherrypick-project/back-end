package com.cherrypick.backend.domain.category;

import com.cherrypick.backend.domain.category.CategoryCriteria.InquiryCategoriesRequest;
import com.cherrypick.backend.domain.category.CategoryInfo.Categories;

public interface CategoryReader {

  Categories findById(InquiryCategoriesRequest request);
}
