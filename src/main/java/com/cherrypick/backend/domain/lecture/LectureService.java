package com.cherrypick.backend.domain.lecture;

import com.cherrypick.backend.domain.category.ThirdCategory;
import com.cherrypick.backend.domain.lecture.LectureCommand.ConditionRequest;
import com.cherrypick.backend.domain.lecture.LectureCommand.CreateLectureCommand;
import com.cherrypick.backend.domain.lecture.LectureInfo.LectureDetail;
import com.cherrypick.backend.domain.lecture.LectureInfo.Lectures;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface LectureService {

  Slice<Lectures> inquiryLectures(ConditionRequest command, Pageable pageable, boolean isMobile);

  LectureDetail inquiryLectureDetail(String loginId, Long lectureId);

  void createLecture(CreateLectureCommand command, List<ThirdCategory> thirdCategory);
}
