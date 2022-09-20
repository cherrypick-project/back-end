package com.cherrypick.backend.domain.lecture;


import com.cherrypick.backend.domain.category.LectureCategory;
import java.util.List;

public interface LectureStore {

  void storeAll(List<LectureCategory> lectureCategories);
}
