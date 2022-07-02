package com.cherrypick.backend.infrastructure.lecture;

import com.cherrypick.backend.domain.lecture.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LectureRepository extends JpaRepository<Lecture, Long>, LectureRepositoryCustom {

}
