package com.cherrypick.backend.infrastructure.review;

import com.cherrypick.backend.domain.review.ReviewInfo.ReviewDetail;
import com.cherrypick.backend.domain.review.ReviewReader;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReviewReaderImpl implements ReviewReader {

  private final ReviewRepositoryImpl reviewRepository;

  @Override
  public List<ReviewDetail> findAllByLectureId(Long lectureId) {
    return reviewRepository.findAllByLectureId(lectureId);
  }
}
