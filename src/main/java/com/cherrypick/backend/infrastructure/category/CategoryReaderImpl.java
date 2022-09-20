package com.cherrypick.backend.infrastructure.category;

import com.cherrypick.backend.common.exception.BusinessException;
import com.cherrypick.backend.common.exception.ErrorCode;
import com.cherrypick.backend.domain.category.CategoryCriteria.InquiryCategoriesRequest;
import com.cherrypick.backend.domain.category.CategoryInfo.Categories;
import com.cherrypick.backend.domain.category.CategoryReader;
import com.cherrypick.backend.domain.category.ThirdCategory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CategoryReaderImpl implements CategoryReader {

  private final CategoryRepositoryQueryDsl categoryRepositoryQueryDsl;
  private final ThirdCategoryRepository thirdCategoryRepository;

  @Override
  public Categories findById(InquiryCategoriesRequest request) {
    int depth = request.getDepth();

    if (depth == 1) {
      return categoryRepositoryQueryDsl.findFirst();
    }

    if (depth == 2) {
      return categoryRepositoryQueryDsl.findSecondById(request.getParentId());
    }

    if (depth == 3) {
      return categoryRepositoryQueryDsl.findThirdById(request.getParentId());
    }

    throw new BusinessException("옳바르지 않은 depth 입니다.", ErrorCode.NOT_FOUND_DEPTH);
  }

  @Override
  public List<ThirdCategory> findThirdCategoryById(List<Long> thirdCategories) {
    return thirdCategoryRepository.findAllById(thirdCategories);
  }
}
