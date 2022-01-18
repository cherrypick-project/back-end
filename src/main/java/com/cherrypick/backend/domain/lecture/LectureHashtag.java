package com.cherrypick.backend.domain.lecture;

import com.cherrypick.backend.domain.hashtag.Hashtag;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RequiredArgsConstructor
public class LectureHashtag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "lectureHashtag", cascade = CascadeType.ALL)
    private List<Hashtag> hashTags = new ArrayList<>();

    @OneToMany(mappedBy = "lectureHashtag", cascade = CascadeType.ALL)
    private List<Lecture> lectures = new ArrayList<>();

}
