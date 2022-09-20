package com.cherrypick.backend.domain.category;

import com.cherrypick.backend.domain.category.CategoryCriteria.InquiryCategoriesRequest;
import com.cherrypick.backend.domain.category.CategoryInfo.Categories;
import java.util.List;

public interface CategoryReader {

  Categories findById(InquiryCategoriesRequest request);

  List<ThirdCategory> findThirdCategoryById(List<Long> thirdCategories);
}
