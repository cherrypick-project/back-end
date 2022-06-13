package com.cherrypick.backend.domain.review;

import com.cherrypick.backend.domain.review.ReviewInfo.CostPerformanceStatistics;
import com.cherrypick.backend.domain.review.ReviewInfo.RatingByJob;
import com.cherrypick.backend.domain.review.ReviewInfo.RecommendationStatistics;
import com.cherrypick.backend.domain.review.ReviewInfo.Statistics;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

  private final ReviewReader reviewReader;

  @Override
  public Statistics inquiryReviewStatics(Long lectureId) {
    List<RatingByJob> ratingByJob = reviewReader.inquiryRatingByJob(lectureId);
    List<RecommendationStatistics> recommendations = reviewReader.inquiryPercentByRecommendation(
      lectureId);
    List<CostPerformanceStatistics> costPerformances = reviewReader.inquiryCountByCostPerformance(
      lectureId);
    return new Statistics(ratingByJob, recommendations, costPerformances);
  }
}
