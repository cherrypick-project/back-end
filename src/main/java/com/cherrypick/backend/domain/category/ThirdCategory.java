package com.cherrypick.backend.domain.category;

import static javax.persistence.FetchType.LAZY;

import com.cherrypick.backend.domain.BaseEntity;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ThirdCategory extends BaseEntity {

  @Id
  @Column(name = "id", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  private String categoryImgUrl;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "second_category_id")
  private SecondCategory category;

  @OneToMany(mappedBy = "category")
  private List<LectureCategory> lectureCategoryList = new ArrayList<>();

  public ThirdCategory(String name, SecondCategory category, String categoryImgUrl) {
    this.name = name;
    this.category = category;
    this.categoryImgUrl = categoryImgUrl;
  }
}