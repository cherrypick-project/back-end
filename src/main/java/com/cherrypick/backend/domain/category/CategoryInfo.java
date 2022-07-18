package com.cherrypick.backend.domain.category;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class CategoryInfo {

  @Getter
  @RequiredArgsConstructor
  public static class Main {

    private final String name;
    private final String categoryImgUrl;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public static Main of(FirstCategory category) {
      return new Main(
        category.getName(),
        category.getCategoryImgUrl(),
        category.getCreatedAt(),
        category.getModifiedAt());
    }

    public static Main of(SecondCategory category) {
      return new Main(
        category.getName(),
        category.getCategoryImgUrl(),
        category.getCreatedAt(),
        category.getModifiedAt());
    }

    public static Main of(ThirdCategory category) {
      return new Main(
        category.getName(),
        category.getCategoryImgUrl(),
        category.getCreatedAt(),
        category.getModifiedAt());
    }
  }

  @Getter
  @RequiredArgsConstructor
  public static class Categories {

    private final List<Main> categories;

    public static Categories ofFirst(List<FirstCategory> categories) {
      return new Categories(
        categories
          .stream()
          .map(Main::of)
          .collect(Collectors.toList())
      );
    }

    public static Categories ofSecond(List<SecondCategory> categories) {
      return new Categories(
        categories
          .stream()
          .map(Main::of)
          .collect(Collectors.toList())
      );
    }

    public static Categories ofThird(List<ThirdCategory> categories) {
      return new Categories(
        categories
          .stream()
          .map(Main::of)
          .collect(Collectors.toList())
      );
    }
  }
}
