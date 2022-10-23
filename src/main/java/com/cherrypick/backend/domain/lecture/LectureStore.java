package com.cherrypick.backend.domain.lecture;


import com.cherrypick.backend.domain.category.LectureCategory;
import com.cherrypick.backend.domain.lecture.LectureCommand.UpdateLectureCommand;
import java.util.List;

public interface LectureStore {

  void storeAll(List<LectureCategory> lectureCategories);

  List<LectureCategory> findAllLectureCategoriesALLByLectureId(Long id);

  Lecture updateLecture(UpdateLectureCommand command);

  void deleteAll(List<LectureCategory> lectureCategories);

}
