package com.cherrypick.backend.domain.category;

import com.cherrypick.backend.domain.category.CategoryCommand.RegisterCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

  private final CategoryStore categoryStore;
  @Override
  public void registerCategory(RegisterCategory request) {
    categoryStore.save(request);
  }
}
