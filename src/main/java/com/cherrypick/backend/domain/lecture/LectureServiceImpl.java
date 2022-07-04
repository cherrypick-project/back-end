package com.cherrypick.backend.domain.lecture;

import com.cherrypick.backend.common.exception.BusinessException;
import com.cherrypick.backend.common.exception.ErrorCode;
import com.cherrypick.backend.domain.lecture.LectureCommand.ConditionRequest;
import com.cherrypick.backend.domain.lecture.LectureInfo.LectureDetail;
import com.cherrypick.backend.domain.lecture.LectureInfo.Lectures;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LectureServiceImpl implements LectureService {

  private final LectureReader lectureReader;

  @Override
  public Page<Lectures> inquiryLectures(ConditionRequest command, Pageable pageable) {
    return lectureReader.findAllLecturePageableByLectureIdAndCategoryIdAndDepth(command, pageable);
  }

  @Override
  public LectureDetail inquiryLectureDetail(String loginId, Long lectureId) {
    return lectureReader.findByLectureId(loginId, lectureId)
      .orElseThrow(() -> new BusinessException(lectureId + "강의를 찾지 못했습니다.",
        ErrorCode.NOT_FOUND_LECTURE));
  }

  @Override
  public Slice<Lectures> inquiryLecturesMobile(
    ConditionRequest command, Pageable pageable) {
    return lectureReader.findAllLectureSliceByLectureIdAndCategoryIdAndDepth(command, pageable);
  }
}
