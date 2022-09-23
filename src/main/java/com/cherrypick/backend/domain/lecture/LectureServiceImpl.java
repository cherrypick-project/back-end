package com.cherrypick.backend.domain.lecture;

import com.cherrypick.backend.common.exception.BusinessException;
import com.cherrypick.backend.common.exception.ErrorCode;
import com.cherrypick.backend.domain.category.LectureCategory;
import com.cherrypick.backend.domain.category.ThirdCategory;
import com.cherrypick.backend.domain.lecture.LectureCommand.ConditionRequest;
import com.cherrypick.backend.domain.lecture.LectureCommand.CreateLectureCommand;
import com.cherrypick.backend.domain.lecture.LectureInfo.LectureDetail;
import com.cherrypick.backend.domain.lecture.LectureInfo.Lectures;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LectureServiceImpl implements LectureService {

  private final LectureReader lectureReader;
  private final LectureStore lectureStore;

  @Override
  public Slice<Lectures> inquiryLectures(
    ConditionRequest command,
    Pageable pageable,
    boolean isMobile) {

    return lectureReader.findAllLectureByLectureIdAndCategoryIdAndDepth(
      command,
      pageable,
      isMobile);
  }

  @Override
  public LectureDetail inquiryLectureDetail(String loginId, Long lectureId) {
    return lectureReader.findByLectureId(loginId, lectureId)
      .orElseThrow(() -> new BusinessException(lectureId + "강의를 찾지 못했습니다.",
        ErrorCode.NOT_FOUND_LECTURE));
  }

  @Override
  public void createLecture(CreateLectureCommand command, List<ThirdCategory> thirdCategories) {
    Lecture lecture = command.toEntity();
    List<LectureCategory> lectureCategories = thirdCategories.stream()
      .map(thirdCategory -> {
        LectureCategory lectureCategory = new LectureCategory(thirdCategory);
        lecture.addLectureCategory(lectureCategory);
        return lectureCategory;
      })
      .collect(Collectors.toList());
    lectureStore.storeAll(lectureCategories);
  }
}
