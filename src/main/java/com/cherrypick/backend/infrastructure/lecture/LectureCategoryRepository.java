package com.cherrypick.backend.infrastructure.lecture;

import com.cherrypick.backend.domain.category.LectureCategory;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LectureCategoryRepository extends JpaRepository<LectureCategory, Long> {

  List<LectureCategory> findALLByLectureId(Long lectureId);

}
