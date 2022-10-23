package com.cherrypick.backend.presentation.category;

import com.cherrypick.backend.application.CategoryFacade;
import com.cherrypick.backend.common.response.CommonResponse;
import com.cherrypick.backend.domain.category.CategoryCommand;
import com.cherrypick.backend.domain.category.CategoryCriteria.InquiryCategoriesRequest;
import com.cherrypick.backend.domain.category.CategoryInfo.Categories;
import com.cherrypick.backend.presentation.category.CategoryDto.InquiryCategoriesResponse;
import com.cherrypick.backend.presentation.category.CategoryDto.InquiryCategoryResponse;
import com.cherrypick.backend.presentation.category.CategoryDto.RegisterCategory;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

  @Operation(summary = "카테고리 생성", responses = {
    @ApiResponse(responseCode = "200", description = "성공"
    )
  })
  @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
  @PostMapping("/admin/v1/category")
  public ResponseEntity<CommonResponse> registerCategory(@RequestBody RegisterCategory request) {
    val command = CategoryCommand.RegisterCategory.of(request);
    categoryFacade.registerCategory(command);
    return ResponseEntity.ok(CommonResponse.success());
  }

  @Operation(
    summary = "카테고리 목록 조회",
    responses = @ApiResponse(responseCode = "200", description = "성공",
      content = @Content(array = @ArraySchema(schema = @Schema(implementation = InquiryCategoryResponse.class, description = "카테고리 정보")))
    ))
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
