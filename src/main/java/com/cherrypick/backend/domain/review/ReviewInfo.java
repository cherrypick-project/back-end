package com.cherrypick.backend.domain.review;

import com.cherrypick.backend.domain.user.User.Career;
import com.querydsl.core.annotations.QueryProjection;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ReviewInfo {

  @Getter
  public static class Statistics {

    private final List<RatingByJob> ratingByJobs;
    private final List<RecommendationStatistics> recommendationStatistics;
    private final List<CostPerformanceStatistics> costPerformanceStatistics;

    public Statistics(
      List<RatingByJob> ratingByJobs,
      List<RecommendationStatistics> recommendationStatistics,
      List<CostPerformanceStatistics> costPerformanceStatistics) {
      this.ratingByJobs = ratingByJobs;
      this.recommendationStatistics = recommendationStatistics;
      this.costPerformanceStatistics = costPerformanceStatistics;
    }
  }

  @Getter
  public static class Statistics2 {

    private double totalRating;
    private long count;
    private double frontRating;
    private double backendRating;
    private Recommendation recommendation;
    private CostPerformance costPerformance;

    private MostViewUserGroup mostViewUserGroup;

    @QueryProjection
    public Statistics2(double totalRating, long count, double frontRating, double backendRating) {
      this.totalRating = totalRating;
      this.count = count;
      this.frontRating = frontRating;
      this.backendRating = backendRating;
    }


    public Statistics2(List<RatingByJob> ratingByJob,
      List<RecommendationStatistics> recommendationStatistics,
      List<CostPerformanceStatistics> costPerformanceStatistics) {

      this.count = ratingByJob.stream()
        .map(RatingByJob::getCount)
        .reduce(Long::sum)
        .orElse(0L);
      this.frontRating = ratingByJob.stream()
        .filter(rating -> rating.getJob().equals("프론트엔드"))
        .map(RatingByJob::getRating)
        .findFirst()
        .orElse(0.0);
      this.backendRating = ratingByJob.stream()
        .filter(rating -> rating.getJob().equals("백엔드"))
        .map(RatingByJob::getRating)
        .findFirst()
        .orElse(0.0);
      this.totalRating =
        ratingByJob.stream().mapToDouble(a -> a.getRating() * a.getCount()).sum() / count;
      this.recommendation = new Recommendation(recommendationStatistics);
      this.costPerformance = new CostPerformance(costPerformanceStatistics);
      this.mostViewUserGroup = new MostViewUserGroup(ratingByJob);
    }
  }

  @Getter
  public static class Recommendation {

    private int good;
    private int bad;

    public Recommendation(List<RecommendationStatistics> recommendationStatistics) {
      long count = recommendationStatistics.stream()
        .map(RecommendationStatistics::getCount)
        .reduce(Long::sum)
        .orElse(0L);
      this.good = recommendationStatistics.stream()
        .filter(r -> r.getRecommendation() == (Review.Recommendation.GOOD))
        .map(recommend -> Math.toIntExact(recommend.getCount() / count) * 100)
        .findFirst()
        .orElse(0);
      this.bad = recommendationStatistics.stream()
        .filter(r -> r.getRecommendation() == (Review.Recommendation.BAD))
        .map(recommend -> Math.toIntExact(recommend.getCount() / count) * 100)
        .findFirst()
        .orElse(0);
    }
  }

  @Getter
  public static class CostPerformance {

    private long verySatisfaction;
    private long satisfaction;
    private long middle;
    private long soso;

    public CostPerformance(List<CostPerformanceStatistics> costPerformanceStatistics) {
      this.verySatisfaction = costPerformanceStatistics.stream()
        .filter(r -> r.getCostPerformance() == (Review.CostPerformance.VERY_SATISFACTION))
        .map(CostPerformanceStatistics::getCount)
        .findFirst()
        .orElse(0L);
      this.satisfaction = costPerformanceStatistics.stream()
        .filter(r -> r.getCostPerformance() == (Review.CostPerformance.SATISFACTION))
        .map(CostPerformanceStatistics::getCount)
        .findFirst()
        .orElse(0L);
      this.middle = costPerformanceStatistics.stream()
        .filter(r -> r.getCostPerformance() == (Review.CostPerformance.MIDDLE))
        .map(CostPerformanceStatistics::getCount)
        .findFirst()
        .orElse(0L);
      this.soso = costPerformanceStatistics.stream()
        .filter(r -> r.getCostPerformance() == (Review.CostPerformance.SOSO))
        .map(CostPerformanceStatistics::getCount)
        .findFirst()
        .orElse(0L);
    }
  }

  @Getter
  @NoArgsConstructor
  public static class RatingByJob {

    private String job;
    private Career career;
    private double rating;
    private long count;

    @QueryProjection
    public RatingByJob(String job, Career career, double rating, long count) {
      this.job = job;
      this.career = career;
      this.rating = rating;
      this.count = count;
    }
  }

  @Getter
  public static class RecommendationStatistics {

    private Review.Recommendation recommendation;
    private long count;

    @QueryProjection
    public RecommendationStatistics(Review.Recommendation recommendation, long count) {
      this.recommendation = recommendation;
      this.count = count;
    }
  }

  @Getter
  public static class CostPerformanceStatistics {

    private Review.CostPerformance costPerformance;
    private long count;

    @QueryProjection
    public CostPerformanceStatistics(Review.CostPerformance costPerformance, long count) {
      this.costPerformance = costPerformance;
      this.count = count;
    }
  }

  @Getter
  @AllArgsConstructor
  public static class MostViewUserGroup {

    private String job;
    private Career career;

    public MostViewUserGroup(List<RatingByJob> ratingByJob) {
      RatingByJob job = ratingByJob.stream().reduce((a, b) -> a.getCount() > b.getRating() ? a : b)
        .orElse(new RatingByJob());
      this.job = job.getJob();
      this.career = job.getCareer();
    }
  }
}
