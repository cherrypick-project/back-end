package com.cherrypick.backend.infrastructure.lecture;

import com.cherrypick.backend.domain.lecture.Lecture;
import com.cherrypick.backend.domain.lecture.LectureCommand.ConditionRequest;
import com.cherrypick.backend.domain.lecture.LectureInfo.LectureDetail;
import com.cherrypick.backend.domain.lecture.LectureInfo.Lectures;
import com.cherrypick.backend.domain.lecture.LectureReader;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LectureReaderImpl implements LectureReader {

  private final LectureRepositoryQueryDsl lectureRepositoryQueryDsl;

  @Override
  public Slice<Lectures> findAllLectureByLectureIdAndCategoryIdAndDepth(
    ConditionRequest command, Pageable pageable, boolean isMobile) {
    return lectureRepositoryQueryDsl.findAllByLectureIdAndCategoryIdAndDepth(
      command,
      pageable,
      isMobile);
  }

  @Override
  public Optional<LectureDetail> findByLectureId(String loginId, Long lectureId) {
    return lectureRepositoryQueryDsl.findByLectureId(loginId, lectureId);
  }

  @Override
  public Optional<Lecture> findByLectureId(Long lectureId) {
    return lectureRepositoryQueryDsl.findByLectureId(lectureId);
  }
}
