package com.cherrypick.backend.domain.review;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

import com.cherrypick.backend.domain.review.Review.CostPerformance;
import com.cherrypick.backend.domain.review.Review.Recommendation;
import com.cherrypick.backend.domain.review.ReviewInfo.CostPerformanceStatics;
import com.cherrypick.backend.domain.review.ReviewInfo.MostViewUserGroup;
import com.cherrypick.backend.domain.review.ReviewInfo.RecommendationStatics;
import com.cherrypick.backend.domain.review.ReviewInfo.ReviewDetail;
import com.cherrypick.backend.domain.review.ReviewInfo.User;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Reviews {

  private static final String FRONT_END = "프론트엔드";
  private static final String BACK_END = "백엔드";
  private final List<ReviewInfo.ReviewDetail> reviews;

  public Reviews(List<ReviewInfo.ReviewDetail> reviews) {
    this.reviews = reviews;
  }

  public Long getCount() {
    return (long) reviews.size();
  }

  public double getTotalRating() {
    return reviews.stream()
      .mapToDouble(ReviewInfo.ReviewDetail::getRating)
      .average().orElse(0);
  }

  public double getFrontEndRating() {
    return reviews.stream()
      .filter(review -> isFrontEnd(review, FRONT_END))
      .mapToDouble(ReviewInfo.ReviewDetail::getRating)
      .average().orElse(0);
  }

  public double getBackEndRating() {
    return reviews.stream()
      .filter(review -> isFrontEnd(review, BACK_END))
      .mapToDouble(ReviewInfo.ReviewDetail::getRating)
      .average().orElse(0);
  }

  public MostViewUserGroup getMostViewUserGroup() {
    Map<User, Long> userGroupCountMap = reviews.stream()
      .collect(groupingBy(ReviewDetail::getUser, counting()));

    Entry<User, Long> userGroupLongEntry = userGroupCountMap.entrySet()
      .stream().max(Entry.comparingByValue()).orElse(null);

    if (userGroupLongEntry == null) {
      return new MostViewUserGroup();
    }
    User userGroup = userGroupLongEntry.getKey();
    return new MostViewUserGroup(userGroup);
  }

  public RecommendationStatics getRecommendationStatics() {
    Map<Recommendation, Long> recommendationCountMap = reviews.stream()
      .collect(groupingBy(ReviewInfo.ReviewDetail::getRecommendation, counting()));

    String good = getRecommendationAndCalculatePercent(recommendationCountMap, Recommendation.GOOD);
    String bad = getRecommendationAndCalculatePercent(recommendationCountMap, Recommendation.BAD);

    return new RecommendationStatics(good, bad);
  }

  public CostPerformanceStatics getCostPerformanceStatics() {
    Map<CostPerformance, Long> costPerformanceCountMap = reviews.stream()
      .collect(groupingBy(ReviewInfo.ReviewDetail::getCostPerformance, counting()));

    Long verySatisfactionCount = getCostPerformanceCount(costPerformanceCountMap,
      CostPerformance.VERY_SATISFACTION);
    Long satisfactionCount = getCostPerformanceCount(costPerformanceCountMap,
      CostPerformance.SATISFACTION);
    Long middleCount = getCostPerformanceCount(costPerformanceCountMap, CostPerformance.MIDDLE);
    Long sosoCount = getCostPerformanceCount(costPerformanceCountMap, CostPerformance.SOSO);

    String verySatisfaction = getCostPerformanceAndCalculatePercent(verySatisfactionCount);
    String satisfaction = getCostPerformanceAndCalculatePercent(satisfactionCount);
    String middle = getCostPerformanceAndCalculatePercent(middleCount);
    String soso = getCostPerformanceAndCalculatePercent(sosoCount);

    return new CostPerformanceStatics(
      verySatisfactionCount,
      satisfactionCount,
      middleCount,
      sosoCount,
      verySatisfaction,
      satisfaction,
      middle,
      soso
    );
  }

  private Long getCostPerformanceCount(Map<CostPerformance, Long> costPerformanceCountMap,
    CostPerformance costPerformance) {
    return costPerformanceCountMap.getOrDefault(costPerformance, 0L);
  }

  private String getCostPerformanceAndCalculatePercent(Long count) {
    if (getCount() == 0 || count == 0) {
      return "0";
    }
    BigDecimal dividedValue = BigDecimal.valueOf(count)
      .divide(BigDecimal.valueOf(getCount()), 3, RoundingMode.HALF_UP);
    BigDecimal percent = dividedValue.multiply(BigDecimal.valueOf(100));
    DecimalFormat percentInstance = new DecimalFormat("#,##0.00");
    return percentInstance.format(percent.doubleValue());
  }

  private String getRecommendationAndCalculatePercent(
    Map<Recommendation, Long> recommendationCountMap,
    Recommendation recommendation) {
    if (getCount() == 0) {
      return "0";
    }
    long count = recommendationCountMap.getOrDefault(recommendation, 0L);
    BigDecimal dividedValue = BigDecimal.valueOf(count)
      .divide(BigDecimal.valueOf(getCount()), 3, RoundingMode.HALF_UP);
    BigDecimal percent = dividedValue.multiply(BigDecimal.valueOf(100));
    DecimalFormat percentInstance = new DecimalFormat("#,##0.00");
    return percentInstance.format(percent.doubleValue());
  }

  private boolean isFrontEnd(ReviewInfo.ReviewDetail review, String job) {
    return review.getUser().getJob().equals(job);
  }
}