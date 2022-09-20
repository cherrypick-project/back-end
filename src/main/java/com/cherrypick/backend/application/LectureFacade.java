package com.cherrypick.backend.application;

import com.cherrypick.backend.domain.category.CategoryService;
import com.cherrypick.backend.domain.category.ThirdCategory;
import com.cherrypick.backend.domain.lecture.LectureCommand.ConditionRequest;
import com.cherrypick.backend.domain.lecture.LectureCommand.CreateLectureCommand;
import com.cherrypick.backend.domain.lecture.LectureInfo.LectureDetail;
import com.cherrypick.backend.domain.lecture.LectureInfo.Lectures;
import com.cherrypick.backend.domain.lecture.LectureService;
import com.cherrypick.backend.domain.review.ReviewInfo;
import com.cherrypick.backend.domain.review.ReviewService;
import com.cherrypick.backend.presentation.lecture.LectureDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LectureFacade {

  private final LectureService lectureService;

  private final ReviewService reviewService;

  private final CategoryService categoryService;

  public Slice<Lectures> inquiryLectures(ConditionRequest command, Pageable pageable,
    boolean isMobile) {
    return lectureService.inquiryLectures(command, pageable, isMobile);
  }

  public LectureDto.LectureDetail inquiryLectureDetail(String loginId,
    Long lectureId) {
    LectureDetail lectureDetail = lectureService.inquiryLectureDetail(loginId, lectureId);
    ReviewInfo.ReviewStatistics reviewStatics = reviewService.inquiryReviewStatics(lectureId);
    return new LectureDto.LectureDetail(lectureDetail, reviewStatics);
  }

  public void createLecture(CreateLectureCommand command) {
    List<ThirdCategory> thirdCategories = categoryService.inquiryThirdCategory(
      command.getThirdCategories());
    lectureService.createLecture(command, thirdCategories);
  }
}
