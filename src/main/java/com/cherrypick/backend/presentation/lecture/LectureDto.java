package com.cherrypick.backend.presentation.lecture;


import com.cherrypick.backend.domain.lecture.LectureCommand;
import com.cherrypick.backend.domain.lecture.LectureInfo;
import com.cherrypick.backend.domain.review.ReviewInfo;
import com.cherrypick.backend.domain.review.ReviewInfo.CostPerformanceStatics;
import com.cherrypick.backend.domain.review.ReviewInfo.RecommendationStatics;
import com.cherrypick.backend.domain.review.ReviewInfo.ReviewStatistics;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Optional;
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

    public LectureCommand.ConditionRequest toCommand(String loginId,
      LectureDto.ConditionRequest request) {

      return new LectureCommand.ConditionRequest(
        request.getSearchName(),
        request.getCategoryId(),
        Optional.ofNullable(depth).orElse(3),
        loginId
      );
    }
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

    private final String good;
    private final String bad;

    public Recommendation(RecommendationStatics recommendationStatics) {
      this.good = recommendationStatics.getGood();
      this.bad = recommendationStatics.getBad();
    }
  }

  @Getter
  @AllArgsConstructor
  public static class CostPerformance {

    private final Long verySatisfactionCount;
    private final Long satisfactionCount;
    private final Long middleCount;
    private final Long sosoCount;
    private final String verySatisfactionPercent;
    private final String satisfactionPercent;
    private final String middlePercent;
    private final String sosoPercent;

    public CostPerformance(CostPerformanceStatics costPerformanceStatics) {
      this.verySatisfactionCount = costPerformanceStatics.getVerySatisfactionCount();
      this.satisfactionCount = costPerformanceStatics.getSatisfactionCount();
      this.middleCount = costPerformanceStatics.getMiddleCount();
      this.sosoCount = costPerformanceStatics.getSosoCount();
      this.verySatisfactionPercent = costPerformanceStatics.getVerySatisfactionPercent();
      this.satisfactionPercent = costPerformanceStatics.getSatisfactionPercent();
      this.middlePercent = costPerformanceStatics.getMiddlePercent();
      this.sosoPercent = costPerformanceStatics.getSosoPercent();
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
    private final String info;


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
      this.info = lectureDetail.getInfo();
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
