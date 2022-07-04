package com.cherrypick.backend.common.util;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.PathBuilder;

public class QueryDslOrderUtil {

  public static OrderSpecifier<?> getSortedSubQueryColumn(Order order, String fieldName) {
    Path<Long> fieldPath = Expressions.path(Long.class, fieldName);
    return new OrderSpecifier(order, fieldPath);
  }

  public static OrderSpecifier<?> getSoredColumn(Order order, Class<?> parents, String prop,
      String metaData) {
    PathBuilder orderByExpression = new PathBuilder(parents, metaData);
    return new OrderSpecifier(order, orderByExpression.get(prop));
  }
}
