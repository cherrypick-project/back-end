package com.cherrypick.backend.domain.hashtag;

import com.cherrypick.backend.domain.lecture.LectureHashtag;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RequiredArgsConstructor
public class Hashtag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="lecture_hashtag_id")
    public LectureHashtag lectureHashTag;
}
