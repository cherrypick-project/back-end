package com.cherrypick.backend.domain.lecture;

import static javax.persistence.FetchType.LAZY;

import com.cherrypick.backend.domain.category.LectureCategory;
import com.cherrypick.backend.domain.lecturecompany.LectureCompany;
import com.cherrypick.backend.domain.lecturer.LectureLecturer;
import com.cherrypick.backend.domain.lecturer.Lecturer;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import jdk.jfr.Unsigned;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Lecture {

  @Id
  @Column(name = "id", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToMany(mappedBy = "lecture")
  private List<LectureCategory> lectureCategoryList = new ArrayList<>();

  private String desktopImgUrl;

  private String tabletImgUrl;

  private String mobileImgUrl;

  private String name;

  private boolean isOffline;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "lecture_company_id")
  private LectureCompany lectureCompany;

  @OneToMany(mappedBy = "lecture" )
  private List<LectureLecturer> lecturer = new ArrayList<>();

  @Convert(converter = HashTagConverter.class)
  private List<String> hashTagList = new ArrayList<>();

  private String originLink;

  private int price;

  private String info;

  private boolean isOpened;

}
