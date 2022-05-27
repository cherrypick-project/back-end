package com.cherrypick.backend.domain.lecture;

import com.cherrypick.backend.domain.BaseEntity;
import com.cherrypick.backend.domain.category.LectureCategory;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Lecture extends BaseEntity {

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

  private String lectureCompany;

  @Convert(converter = SeparatorConverter.class)
  private List<String> lecturer = new ArrayList<>();

  @Convert(converter = SeparatorConverter.class)
  private List<String> hashTagList = new ArrayList<>();

  private String originLink;

  private int price;

  private String info;

  private boolean isOpened;

}
