package com.cherrypick.backend.infrastructure.review;

import com.cherrypick.backend.domain.review.ReviewInfo.ReviewDetail;
import com.cherrypick.backend.domain.review.ReviewReader;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReviewReaderImpl implements ReviewReader {

  private final ReviewRepositoryQueryDsl reviewRepositoryQueryDsl;

  @Override
  public List<ReviewDetail> findAllByLectureId(Long lectureId) {
    return reviewRepositoryQueryDsl.findAllByLectureId(lectureId);
  }

  @Override
  public Page<ReviewDetail> findAllReviewPageableByLectureId(Long lectureId,
    Pageable pageable) {
    return reviewRepositoryQueryDsl.findAllReviewPageableByLectureId(lectureId, pageable);
  }

  @Override
  public Slice<ReviewDetail> findAllReviewSliceByLectureId(Long lectureId,
    Pageable pageable) {
    return reviewRepositoryQueryDsl.findAllReviewSliceByLectureId(lectureId, pageable);
  }
}
