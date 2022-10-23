package com.cherrypick.backend.infrastructure.lecture;


import com.cherrypick.backend.common.exception.BusinessException;
import com.cherrypick.backend.common.exception.ErrorCode;
import com.cherrypick.backend.domain.category.LectureCategory;
import com.cherrypick.backend.domain.lecture.Lecture;
import com.cherrypick.backend.domain.lecture.LectureCommand.UpdateLectureCommand;
import com.cherrypick.backend.domain.lecture.LectureStore;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class LectureStoreImpl implements LectureStore {

  private final LectureRepository lectureRepository;
  private final LectureCategoryRepository lectureCategoryRepository;

  @Override
  public void storeAll(List<LectureCategory> lectureCategories) {
    lectureCategoryRepository.saveAll(lectureCategories);
  }

  @Override
  public List<LectureCategory> findAllLectureCategoriesALLByLectureId(Long id) {
    return lectureCategoryRepository.findALLByLectureId(id);
  }

  @Override
  @Transactional
  public Lecture updateLecture(UpdateLectureCommand command) {
    Lecture lecture = lectureRepository.findById(command.getId())
      .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_LECTURE));
    lecture.update(command);
    return lecture;
  }

  @Override
  @Transactional
  public void deleteAll(List<LectureCategory> lectureCategories) {
    lectureCategories.forEach(LectureCategory::delete);
    lectureCategoryRepository.deleteAll(lectureCategories);
  }
}
