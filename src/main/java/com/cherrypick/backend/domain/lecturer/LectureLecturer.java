package com.cherrypick.backend.domain.lecturer;

import static javax.persistence.FetchType.LAZY;

import com.cherrypick.backend.domain.lecture.Lecture;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class LectureLecturer {

  @Id
  @Column(name = "id", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "lecture_id")
  private Lecture lecture;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "lecturer_id")
  private Lecturer lecturer;
}
