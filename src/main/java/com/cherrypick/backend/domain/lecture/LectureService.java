package com.cherrypick.backend.domain.lecture;

import com.cherrypick.backend.domain.lecture.LectureCommand.ConditionRequest;
import com.cherrypick.backend.domain.lecture.LectureInfo.LectureDetail;
import com.cherrypick.backend.domain.lecture.LectureInfo.Lectures;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface LectureService {

  Slice<Lectures> inquiryLectures(ConditionRequest command, Pageable pageable, boolean isMobile);

  LectureDetail inquiryLectureDetail(String loginId, Long lectureId);
}
