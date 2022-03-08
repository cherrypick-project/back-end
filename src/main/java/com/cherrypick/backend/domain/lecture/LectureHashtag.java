package com.cherrypick.backend.domain.lecture;

import com.cherrypick.backend.domain.hashtag.Hashtag;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
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
public class LectureHashtag {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

//  @OneToMany(mappedBy = "lectureHashtag", cascade = CascadeType.ALL)
//  private List<Hashtag> hashTags = new ArrayList<>();

  @OneToMany(mappedBy = "lectureHashtag", cascade = CascadeType.ALL)
  private List<Lecture> lectures = new ArrayList<>();
}
