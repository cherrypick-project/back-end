package com.cherrypick.backend.domain.lecture;

import com.cherrypick.backend.domain.lecturer.Lecturer;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LectureLecturer {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToMany(mappedBy = "lectureLecturer")
  private List<Lecture> lectures = new ArrayList<>();

  @OneToMany(mappedBy = "lectureLecturer")
  private List<Lecturer> lecturers = new ArrayList<>();
}
