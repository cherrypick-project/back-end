package com.cherrypick.backend.domain.category;

import com.cherrypick.backend.domain.category.CategoryCommand.RegisterCategory;
import com.cherrypick.backend.domain.category.CategoryCriteria.InquiryCategoriesRequest;
import com.cherrypick.backend.domain.category.CategoryInfo.Categories;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

  private final CategoryStore categoryStore;
  private final CategoryReader categoryReader;

  @Override
  public void registerCategory(RegisterCategory request) {
    categoryStore.save(request);
  }

  @Override
  public Categories inquiryCategories(InquiryCategoriesRequest request) {
    return categoryReader.findById(request);
  }
}
