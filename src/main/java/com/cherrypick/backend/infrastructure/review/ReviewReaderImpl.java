package com.cherrypick.backend.infrastructure.review;

import com.cherrypick.backend.domain.review.ReviewInfo.CostPerformanceStatistics;
import com.cherrypick.backend.domain.review.ReviewInfo.RatingByJob;
import com.cherrypick.backend.domain.review.ReviewInfo.RecommendationStatistics;
import com.cherrypick.backend.domain.review.ReviewReader;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReviewReaderImpl implements ReviewReader {

  private final ReviewRepositoryImpl reviewRepository;

  @Override
  public List<RatingByJob> inquiryRatingByJob(Long lectureId) {
    return reviewRepository.findRatingByJobByLectureId(lectureId);
  }

  @Override
  public List<RecommendationStatistics> inquiryPercentByRecommendation(Long lectureId) {
    return reviewRepository.findRecommendationStatisticsByLectureId(lectureId);
  }

  @Override
  public List<CostPerformanceStatistics> inquiryCountByCostPerformance(Long lectureId) {
    return reviewRepository.findCostPerformanceStatisticsByLectureId(lectureId);
  }
}
