package com.cherrypick.backend.infrastructure.category;

import com.cherrypick.backend.common.exception.BusinessException;
import com.cherrypick.backend.common.exception.ErrorCode;
import com.cherrypick.backend.domain.category.CategoryCommand.RegisterCategory;
import com.cherrypick.backend.domain.category.CategoryStore;
import com.cherrypick.backend.domain.category.FirstCategory;
import com.cherrypick.backend.domain.category.SecondCategory;
import com.cherrypick.backend.domain.category.ThirdCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CategoryStoreImpl implements CategoryStore {

  private final FirstCategoryRepository firstCategoryRepository;
  private final SecondCategoryRepository secondCategoryRepository;
  private final ThirdCategoryRepository thirdCategoryRepository;

  @Override
  public void save(RegisterCategory request) {
    int depth = request.getDepth();

    if (depth == 1) {
      firstCategoryRepository.save(
        new FirstCategory(
          request.getName(),
          request.getCategoryImgUrl()));

      return;
    }

    if (depth == 2) {
      FirstCategory firstCategory = firstCategoryRepository
        .findById(request.getParentId())
        .orElseThrow(
          () -> new BusinessException("옳바르지 않은 parent_id 입니다.", ErrorCode.INVALID_PARENT_ID));

      secondCategoryRepository.save(
        new SecondCategory(
          request.getName(),
          firstCategory,
          request.getCategoryImgUrl()));

      return;
    }

    if (depth == 3) {
      SecondCategory secondCategory = secondCategoryRepository
        .findById(request.getParentId())
        .orElseThrow(
          () -> new BusinessException("옳바르지 않은 parent_id 입니다.", ErrorCode.INVALID_PARENT_ID));

      thirdCategoryRepository.save(
        new ThirdCategory(
          request.getName(),
          secondCategory,
          request.getCategoryImgUrl()));

      return;
    }

    throw new BusinessException("옳바르지 않은 depth 입니다.", ErrorCode.NOT_FOUND_DEPTH);
  }
}
