package com.cherrypick.backend.domain.lecture;

import com.querydsl.core.annotations.QueryProjection;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

public class LectureInfo {

  @Getter
  public static class Lectures {

    private Long id;
    private String desktopImgUrl;
    private String tabletImgUrl;
    private String mobileImgUrl;
    private String name;
    private boolean isOffline;
    private String lectureCompany;
    private List<String> lecturers;
    private List<String> hashTags;
    private String originLink;
    private int price;
    private long reviewCount;
    private double rating;
    private boolean isBookmark;

    @QueryProjection
    public Lectures(Long id, String desktopImgUrl, String tabletImgUrl, String mobileImgUrl,
      String name, boolean isOffline, String lectureCompany,
      List<String> lecturers, List<String> hashTags, String originLink, int price,
      long reviewCount,
      double rating, boolean isBookmark) {
      this.id = id;
      this.desktopImgUrl = desktopImgUrl;
      this.tabletImgUrl = tabletImgUrl;
      this.mobileImgUrl = mobileImgUrl;
      this.name = name;
      this.isOffline = isOffline;
      this.lectureCompany = lectureCompany;
      this.lecturers = lecturers;
      this.hashTags = hashTags;
      this.originLink = originLink;
      this.price = price;
      this.reviewCount = reviewCount;
      this.rating = rating;
      this.isBookmark = isBookmark;
    }
  }

  @Getter
  @Setter
  public static class LectureDetail {

    private Long id;
    private String desktopImgUrl;
    private String tabletImgUrl;
    private String mobileImgUrl;
    private String name;
    private boolean isOffline;
    private String lectureCompany;
    private List<String> lecturers;
    private List<String> hashTags;
    private double rating;
    private long reviewCount;
    private String originLink;
    private int price;
    private boolean isBookmark;

    @QueryProjection
    public LectureDetail(Long id, String desktopImgUrl, String tabletImgUrl,
      String mobileImgUrl, String name, boolean isOffline, String lectureCompany,
      List<String> lecturers, List<String> hashTags, String originLink, int price,
      boolean isBookmark) {
      this.id = id;
      this.desktopImgUrl = desktopImgUrl;
      this.tabletImgUrl = tabletImgUrl;
      this.mobileImgUrl = mobileImgUrl;
      this.name = name;
      this.isOffline = isOffline;
      this.lectureCompany = lectureCompany;
      this.lecturers = lecturers;
      this.hashTags = hashTags;
      this.originLink = originLink;
      this.price = price;
      this.isBookmark = isBookmark;
    }
  }
}
