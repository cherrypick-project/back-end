package com.cherrypick.backend.domain.category;

import com.cherrypick.backend.domain.BaseEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class FirstCategory extends BaseEntity {

  @Id
  @Column(name = "id", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  private String categoryImgUrl;

  public FirstCategory(String name, String categoryImgUrl) {
    this.name = name;
    this.categoryImgUrl = categoryImgUrl;
  }
}
