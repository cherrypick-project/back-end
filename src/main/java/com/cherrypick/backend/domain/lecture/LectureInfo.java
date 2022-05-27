package com.cherrypick.backend.domain.lecture;

import com.querydsl.core.annotations.QueryProjection;
import java.util.List;
import javax.persistence.Convert;
import lombok.Getter;

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
    @Convert(converter = SeparatorConverter.class)
    private List<String> lecturer;
    @Convert(converter = SeparatorConverter.class)
    private List<String> hashTagList;
    private String originLink;
    private int price;
    private long reviewCount;
    private double rating;
    private boolean isBookMark;

    @QueryProjection
    public Lectures(Long id, String desktopImgUrl, String tabletImgUrl, String mobileImgUrl,
        String name, boolean isOffline, String lectureCompany,
        List<String> lecturer, List<String> hashTagList, String originLink, int price,
        long reviewCount,
        double rating, boolean isBookMark) {
      this.id = id;
      this.desktopImgUrl = desktopImgUrl;
      this.tabletImgUrl = tabletImgUrl;
      this.mobileImgUrl = mobileImgUrl;
      this.name = name;
      this.isOffline = isOffline;
      this.lectureCompany = lectureCompany;
      this.lecturer = lecturer;
      this.hashTagList = hashTagList;
      this.originLink = originLink;
      this.price = price;
      this.reviewCount = reviewCount;
      this.rating = rating;
      this.isBookMark = isBookMark;
    }
  }
}
