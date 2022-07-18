package com.cherrypick.backend.infrastructure.category;

import static com.cherrypick.backend.domain.category.QFirstCategory.firstCategory;
import static com.cherrypick.backend.domain.category.QSecondCategory.secondCategory;
import static com.cherrypick.backend.domain.category.QThirdCategory.thirdCategory;

import com.cherrypick.backend.domain.category.CategoryInfo;
import com.cherrypick.backend.domain.category.CategoryInfo.Categories;
import com.cherrypick.backend.domain.category.FirstCategory;
import com.cherrypick.backend.domain.category.SecondCategory;
import com.cherrypick.backend.domain.category.ThirdCategory;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CategoryRepositoryQueryDsl {

  private final JPAQueryFactory queryFactory;

  public Categories findFirst() {
    List<FirstCategory> result = queryFactory
      .selectFrom(firstCategory)
      .fetch();

    return CategoryInfo.Categories.ofFirst(result);
  }

  public Categories findSecondById(Long parentId) {
    List<SecondCategory> result = queryFactory.selectFrom(secondCategory)
      .where(
        parentIdEqSecond(parentId)
      )
      .fetch();

    return CategoryInfo.Categories.ofSecond(result);
  }

  public Categories findThirdById(Long parentId) {
    List<ThirdCategory> result = queryFactory.selectFrom(thirdCategory)
      .where(
        parentIdEqThird(parentId)
      )
      .fetch();

    return CategoryInfo.Categories.ofThird(result);
  }

  private Predicate parentIdEqSecond(Long parentId) {
    return parentId == null ? null : secondCategory.id.eq(parentId);
  }

  private Predicate parentIdEqThird(Long parentId) {
    return parentId == null ? null : thirdCategory.id.eq(parentId);
  }
}
