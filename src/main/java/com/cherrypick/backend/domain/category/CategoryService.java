package com.cherrypick.backend.domain.category;

import static com.cherrypick.backend.domain.category.CategoryInfo.Categories;

import com.cherrypick.backend.domain.category.CategoryCriteria.InquiryCategoriesRequest;
import java.util.List;

public interface CategoryService {

  void registerCategory(CategoryCommand.RegisterCategory request);

  Categories inquiryCategories(InquiryCategoriesRequest request);

  List<ThirdCategory> inquiryThirdCategory(List<Long> thirdCategories);
}
