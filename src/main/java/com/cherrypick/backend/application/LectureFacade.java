package com.cherrypick.backend.application;

import com.cherrypick.backend.domain.lecture.LectureCommand.ConditionRequest;
import com.cherrypick.backend.domain.lecture.LectureInfo.Lectures;
import com.cherrypick.backend.domain.lecture.LectureService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LectureFacade {

  public final LectureService lectureService;

  public Page<Lectures> inquiryLectures(ConditionRequest command, Pageable pageable) {
    return lectureService.inquiryLectures(command, pageable);
  }
}
