package com.cherrypick.backend.domain.review;

import com.cherrypick.backend.domain.review.ReviewInfo.CostPerformanceStatistics;
import com.cherrypick.backend.domain.review.ReviewInfo.RatingByJob;
import com.cherrypick.backend.domain.review.ReviewInfo.RecommendationStatistics;
import java.util.List;

public interface ReviewReader {

  List<RatingByJob> inquiryRatingByJob(Long lectureId);

  List<RecommendationStatistics> inquiryPercentByRecommendation(Long lectureId);

  List<CostPerformanceStatistics> inquiryCountByCostPerformance(Long lectureId);

}
