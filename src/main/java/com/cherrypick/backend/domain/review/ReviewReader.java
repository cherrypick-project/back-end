package com.cherrypick.backend.domain.review;

import com.cherrypick.backend.domain.review.ReviewInfo.ReviewDetail;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface ReviewReader {

  List<ReviewDetail> findAllByLectureId(Long lectureId);

  Slice<ReviewDetail> findAllReviewPageableByLectureId(Long lectureId,
    Pageable pageable, Boolean isMobile);


  Optional<Long> findMaxId();

  List<ReviewDetail> findAllPreviewReviewInIds(List<Long> previewReviewIds);

  Page<ReviewInfo.Review> findAllByLoginId(String loginId, Pageable pageable);

  ReviewInfo.Review findById(Long reviewId);
}
