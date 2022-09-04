package com.cherrypick.backend.infrastructure.review;

import com.cherrypick.backend.domain.review.ReviewInfo;
import com.cherrypick.backend.domain.review.ReviewInfo.ReviewDetail;
import com.cherrypick.backend.domain.review.ReviewReader;
import java.util.List;
import java.util.Optional;
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
  public Slice<ReviewDetail> findAllReviewPageableByLectureId(Long lectureId,
    Pageable pageable, Boolean isMobile) {
    return reviewRepositoryQueryDsl.findAllReviewPageableByLectureId(lectureId, pageable, isMobile);
  }

  @Override
  public Optional<Long> findMaxId() {
    return reviewRepositoryQueryDsl.findMaxId();
  }

  @Override
  public List<ReviewDetail> findAllPreviewReviewInIds(List<Long> previewReviewIds) {
    return reviewRepositoryQueryDsl.findAllPreviewReviewInIds(previewReviewIds);
  }

  @Override
  public Page<ReviewInfo.Review> findAllByLoginId(String loginId, Pageable pageable) {
    return reviewRepositoryQueryDsl.findAllByLoginId(loginId, pageable);
  }
}
