package com.cherrypick.backend.presentation.lecture;


import com.cherrypick.backend.domain.lecture.LectureInfo.LectureDetail;
import com.cherrypick.backend.domain.review.ReviewInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class LectureDto {

  @Getter
  @AllArgsConstructor
  public static class ConditionRequest {

    private String searchName;
    @JsonProperty("categoryId")
    private List<Long> categoryId;
    private Integer depth;
    private String providerId;
  }

  @Getter
  @AllArgsConstructor
  public static class LectureDetailResponse {

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
    private boolean isBookmark;
    private Statistics statistics;

    public LectureDetailResponse(LectureDetail lectureDetail, ReviewInfo.Statistics statistics) {
      this.id = lectureDetail.getId();
      this.desktopImgUrl = lectureDetail.getDesktopImgUrl();
      this.tabletImgUrl = lectureDetail.getTabletImgUrl();
      this.mobileImgUrl = lectureDetail.getMobileImgUrl();
      this.name = lectureDetail.getName();
      this.isOffline = lectureDetail.isOffline();
      this.lectureCompany = lectureDetail.getLectureCompany();
      this.lecturers = lectureDetail.getLecturers();
      this.hashTags = lectureDetail.getHashTags();
      this.originLink = lectureDetail.getOriginLink();
      this.price = lectureDetail.getPrice();
      this.isBookmark = lectureDetail.isBookmark();

    }
  }

  @Getter
  @AllArgsConstructor
  public static class Statistics {

    private double totalRating;
    private long count;
    private double frontRating;
    private double backendRating;
    private Recommendation recommendation;
    private CostPerformance costPerformance;
  }

  @Getter
  @AllArgsConstructor
  public static class Recommendation {

    private int good;
    private int bad;
  }

  @Getter
  @AllArgsConstructor
  public static class CostPerformance {

    private long verySatisfaction;
    private long satisfaction;
    private long middle;
    private long soso;
  }
}
