package com.cherrypick.backend.application;

import com.cherrypick.backend.domain.category.CategoryCommand;
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
}
