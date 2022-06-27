package com.cherrypick.backend.presentation.lecture;


import com.cherrypick.backend.domain.lecture.LectureInfo;
import com.cherrypick.backend.domain.review.ReviewInfo;
import com.cherrypick.backend.domain.review.ReviewInfo.CostPerformanceStatics;
import com.cherrypick.backend.domain.review.ReviewInfo.RecommendationStatics;
import com.cherrypick.backend.domain.review.ReviewInfo.ReviewStatistics;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class LectureDto {

  @Getter
  @AllArgsConstructor
  public static class ConditionRequest {

    private String searchName;
    @JsonProperty("categoryId")
    private List<Long> categoryId;
    private Integer depth;
    @Setter
    private String providerId;
    private boolean isMobile;
  }

  @Getter
  @AllArgsConstructor
  public static class MostViewUserGroup {

    private final String job;
    private final String career;

    public MostViewUserGroup(ReviewInfo.MostViewUserGroup mostViewUserGroup) {
      this.job = mostViewUserGroup.getJob();
      this.career = mostViewUserGroup.getCareer().name();
    }
  }

  @Getter
  @AllArgsConstructor
  public static class Recommendation {

    private final double good;
    private final double bad;

    public Recommendation(RecommendationStatics recommendationStatics) {
      this.good = recommendationStatics.getGood();
      this.bad = recommendationStatics.getBad();
      ;
    }
  }

  @Getter
  @AllArgsConstructor
  public static class CostPerformance {

    private final double verySatisfaction;
    private final double satisfaction;
    private final double middle;
    private final double soso;

    public CostPerformance(CostPerformanceStatics costPerformanceStatics) {
      this.verySatisfaction = costPerformanceStatics.getVerySatisfaction();
      this.satisfaction = costPerformanceStatics.getSatisfaction();
      this.middle = costPerformanceStatics.getMiddle();
      this.soso = costPerformanceStatics.getSoso();
    }
  }

  @Getter
  public static class LectureDetail {

    private final Long id;
    private final String desktopImgUrl;
    private final String tabletImgUrl;
    private final String mobileImgUrl;
    private final String name;
    private final String lectureCompany;
    private final List<String> lecturers;
    private final List<String> hashTags;

    private final Review review;

    private final String originLink;
    private final int price;
    private final MostViewUserGroup mostViewUserGroup;
    private final boolean isBookmark;
    private final boolean isOffline;


    public LectureDetail(LectureInfo.LectureDetail lectureDetail,
      ReviewStatistics reviewStatics) {
      this.id = lectureDetail.getId();
      this.desktopImgUrl = lectureDetail.getDesktopImgUrl();
      this.tabletImgUrl = lectureDetail.getTabletImgUrl();
      this.mobileImgUrl = lectureDetail.getMobileImgUrl();
      this.name = lectureDetail.getName();
      this.lectureCompany = lectureDetail.getLectureCompany();
      this.lecturers = lectureDetail.getLecturers();
      this.hashTags = lectureDetail.getHashTags();
      this.originLink = lectureDetail.getOriginLink();
      this.price = lectureDetail.getPrice();
      this.mostViewUserGroup = new MostViewUserGroup(reviewStatics.getMostViewUserGroup());
      this.isBookmark = lectureDetail.isBookmark();
      this.isOffline = lectureDetail.isOffline();
      this.review = new Review(
        reviewStatics.getTotalRating(),
        reviewStatics.getCount(),
        reviewStatics.getFrontendRating(),
        reviewStatics.getBackendRating(),
        reviewStatics.getRecommendationStatics(),
        reviewStatics.getCostPerformanceStatics());
    }
  }

  @Getter
  public static class Review {

    private final double totalRating;
    private final long count;
    private final double frontendRating;
    private final double backendRating;
    private final Recommendation recommendation;
    private final CostPerformance costPerformance;


    public Review(double totalRating, long count, double frontendRating, double backendRating,
      RecommendationStatics recommendationStatics, CostPerformanceStatics costPerformanceStatics) {
      this.totalRating = totalRating;
      this.count = count;
      this.frontendRating = frontendRating;
      this.backendRating = backendRating;
      this.recommendation = new Recommendation(recommendationStatics);
      this.costPerformance = new CostPerformance(costPerformanceStatics);
    }
  }
}
