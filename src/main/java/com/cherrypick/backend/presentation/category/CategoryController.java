package com.cherrypick.backend.presentation.category;

import com.cherrypick.backend.application.CategoryFacade;
import com.cherrypick.backend.common.response.CommonResponse;
import com.cherrypick.backend.domain.category.CategoryCommand;
import com.cherrypick.backend.domain.category.CategoryCriteria.InquiryCategoriesRequest;
import com.cherrypick.backend.domain.category.CategoryInfo.Categories;
import com.cherrypick.backend.presentation.category.CategoryDto.InquiryCategoriesResponse;
import com.cherrypick.backend.presentation.category.CategoryDto.RegisterCategory;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CategoryController {

  private final CategoryFacade categoryFacade;

  @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
  @PostMapping("/admin/v1/category")
  public ResponseEntity<CommonResponse> registerCategory(@RequestBody RegisterCategory request) {
    val command = CategoryCommand.RegisterCategory.of(request);
    categoryFacade.registerCategory(command);
    return ResponseEntity.ok(CommonResponse.success());
  }

  @PreAuthorize("hasAnyRole('ROLE_USER') or hasAnyRole('ROLE_ADMIN') or hasAnyRole('ROLE_MEMBERSHIP')")
  @GetMapping(value = {"/user/v1/category", "/admin/v1/category"})
  public ResponseEntity<CommonResponse> inquiryCategories(
    CategoryDto.InquiryCategoriesRequest request) {
    val criteria = InquiryCategoriesRequest.of(request);
    Categories categories = categoryFacade.inquiryCategories(criteria);
    val response = InquiryCategoriesResponse.of(categories);
    return ResponseEntity.ok(CommonResponse.success(response));
  }
}
