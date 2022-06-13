package com.cherrypick.backend.domain.lecture;

import com.cherrypick.backend.domain.lecture.LectureCommand.ConditionRequest;
import com.cherrypick.backend.domain.lecture.LectureInfo.LectureDetail;
import com.cherrypick.backend.domain.lecture.LectureInfo.Lectures;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LectureService {

  Page<Lectures> inquiryLectures(ConditionRequest command, Pageable principal);

  LectureDetail inquiryLectureDetail(String loginId, Long lectureId);
}
