package com.cherrypick.backend.domain.lecture;

import com.cherrypick.backend.domain.lecture.LectureCommand.ConditionRequest;
import com.cherrypick.backend.domain.lecture.LectureInfo.LectureDetail;
import com.cherrypick.backend.domain.lecture.LectureInfo.Lectures;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LectureServiceImpl implements LectureService {

  private final LectureReader lectureReader;

  @Override
  public Page<Lectures> inquiryLectures(ConditionRequest command, Pageable pageable) {
    return lectureReader.inquiryLectures(command, pageable);
  }

  @Override
  public LectureDetail inquiryLectureDetail(String loginId, Long lectureId) {
    return lectureReader.inquiryLectureDetail(loginId, lectureId);
  }
}
