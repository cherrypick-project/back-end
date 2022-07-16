package com.cherrypick.backend.domain.lecture;

import com.cherrypick.backend.domain.lecture.LectureCommand.ConditionRequest;
import com.cherrypick.backend.domain.lecture.LectureInfo.LectureDetail;
import com.cherrypick.backend.domain.lecture.LectureInfo.Lectures;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface LectureReader {

  Page<Lectures> findAllLecturePageableByLectureIdAndCategoryIdAndDepth(ConditionRequest command,
    Pageable pageable);

  Optional<LectureDetail> findByLectureId(String loginId, Long lectureId);

  Slice<Lectures> findAllLectureSliceByLectureIdAndCategoryIdAndDepth(ConditionRequest command,
    Pageable pageable);

  Optional<Lecture> findByLectureId(Long lectureId);
}
