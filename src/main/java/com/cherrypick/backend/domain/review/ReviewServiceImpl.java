package com.cherrypick.backend.domain.review;

import com.cherrypick.backend.domain.review.ReviewInfo.ReviewStatistics;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

  private final ReviewReader reviewReader;

  @Override
  public ReviewStatistics inquiryReviewStatics(Long lectureId) {
    List<ReviewInfo.ReviewDetail> reviewList = reviewReader.findAllByLectureId(lectureId);
    Reviews reviews = new Reviews(reviewList);

    return new ReviewInfo.ReviewStatistics(
      reviews.getTotalRating(),
      reviews.getCount(),
      reviews.getFrontEndRating(),
      reviews.getBackEndRating(),
      reviews.getRecommendationStatics(),
      reviews.getCostPerformanceStatics(),
      reviews.getMostViewUserGroup());
  }
}
