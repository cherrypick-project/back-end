package com.cherrypick.backend.domain.category;

import static javax.persistence.FetchType.LAZY;

import com.cherrypick.backend.domain.lecture.Lecture;
import javax.persistence.CascadeType;
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
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LectureCategory {

  @Id
  @Column(name = "id", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = LAZY, cascade = CascadeType.PERSIST)
  @JoinColumn(name = "lecture_id")
  private Lecture lecture;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "thrid_category_id")
  private ThirdCategory category;

  public LectureCategory(ThirdCategory category) {
    this.category = category;
  }

  public void addLecture(Lecture lecture) {
    this.lecture = lecture;
  }

  public void delete() {
    if (this.category != null) {
      this.category.getLectureCategoryList().remove(this);
      this.category = null;
    }
    if (this.lecture != null) {
      this.lecture.getLectureCategoryList().remove(this);
      this.lecture = null;
    }
  }
}

