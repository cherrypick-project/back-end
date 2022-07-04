package com.cherrypick.backend.domain.review;

import com.cherrypick.backend.domain.review.ReviewInfo.ReviewDetail;
import java.util.List;

public interface ReviewReader {

  List<ReviewDetail> findAllByLectureId(Long lectureId);
}
