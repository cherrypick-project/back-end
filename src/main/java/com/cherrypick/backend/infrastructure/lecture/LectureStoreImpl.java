package com.cherrypick.backend.infrastructure.lecture;


import com.cherrypick.backend.domain.category.LectureCategory;
import com.cherrypick.backend.domain.lecture.LectureStore;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LectureStoreImpl implements LectureStore {

  private final LectureCategoryRepository lectureCategoryRepository;

  @Override
  public void storeAll(List<LectureCategory> lectureCategories) {
    lectureCategoryRepository.saveAll(lectureCategories);
  }
}
