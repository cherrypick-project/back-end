package com.cherrypick.backend.infrastructure.lecture;

import com.cherrypick.backend.domain.category.LectureCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LectureCategoryRepository extends JpaRepository<LectureCategory, Long> {

}
