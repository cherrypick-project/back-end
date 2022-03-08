package com.cherrypick.backend.domain.hashtag;

import com.cherrypick.backend.domain.lecture.LectureHashtag;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
public class Hashtag {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

//  @ManyToOne(fetch = FetchType.LAZY)
//  @JoinColumn(name = "lecture_hashtag_id")
//  public LectureHashtag lectureHashTag;
}
