package com.cherrypick.backend.application;

import com.cherrypick.backend.domain.lecture.LectureCommand.ConditionRequest;
import com.cherrypick.backend.domain.lecture.LectureInfo.LectureDetail;
import com.cherrypick.backend.domain.lecture.LectureInfo.Lectures;
import com.cherrypick.backend.domain.lecture.LectureService;
import com.cherrypick.backend.domain.review.ReviewInfo;
import com.cherrypick.backend.domain.review.ReviewService;
import com.cherrypick.backend.presentation.lecture.LectureDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LectureFacade {

  private final LectureService lectureService;

  private final ReviewService reviewService;

  public Page<Lectures> inquiryLectures(ConditionRequest command, Pageable pageable) {
    return lectureService.inquiryLectures(command, pageable);
  }

  public LectureDetail inquiryLectureDetail(String loginId, Long lectureId) {
    LectureDetail lectureDetail = lectureService.inquiryLectureDetail(loginId, lectureId);
    ReviewInfo.Statistics statistics = reviewService.inquiryReviewStatics(lectureId);
    return lectureDetail;
  }
}
