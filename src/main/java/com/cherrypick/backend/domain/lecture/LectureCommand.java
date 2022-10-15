package com.cherrypick.backend.domain.lecture;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class LectureCommand {

  @Getter
  @AllArgsConstructor
  public static class ConditionRequest {

    private String searchName;
    private List<Long> categoryId;
    private int depth;
    private String providerId;
  }

  @Getter
  @AllArgsConstructor
  public static class CreateLectureCommand {

    private String desktopImgUrl;
    private String tabletImgUrl;
    private String mobileImgUrl;
    private List<Long> thirdCategories;
    private String name;
    private boolean offline;
    private String lectureCompany;
    private String originLink;
    private int price;
    private String info;
    private List<String> hashTags;
    private List<String> lecturers;

    public Lecture toEntity() {
      return new Lecture(
        desktopImgUrl,
        tabletImgUrl,
        mobileImgUrl,
        name,
        offline,
        lectureCompany,
        lecturers,
        hashTags,
        originLink,
        price,
        info);
    }
  }

  @Getter
  @AllArgsConstructor
  public static class UpdateLectureCommand {

    private Long id;
    private String desktopImgUrl;
    private String tabletImgUrl;
    private String mobileImgUrl;
    private List<Long> thirdCategories;
    private String name;
    private boolean offline;
    private String lectureCompany;
    private String originLink;
    private int price;
    private String info;
    private List<String> hashTags;
    private List<String> lecturers;

  }
}
