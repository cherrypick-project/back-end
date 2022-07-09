package com.cherrypick.backend.domain.review;

import com.cherrypick.backend.domain.review.ReviewInfo.ReviewDetail;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface ReviewReader {

  List<ReviewDetail> findAllByLectureId(Long lectureId);

  Page<ReviewDetail> findAllReviewPageableByLectureId(Long lectureId,
    Pageable pageable);

  Slice<ReviewDetail> findAllReviewSliceByLectureId(Long lectureId,
    Pageable pageable);

  Optional<Long> findMaxId();

  List<ReviewDetail> findAllPreviewReviewInIds(List<Long> previewReviewIds);
}
