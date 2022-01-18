package com.cherrypick.backend.domain.lecture;

import com.cherrypick.backend.domain.bookmark.Bookmark;
import com.cherrypick.backend.domain.review.Review;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RequiredArgsConstructor
public class Lecture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int price;

    private String name;

    private Long platformId;

    private String lectureLink;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecture_hashtag_id")
    private LectureHashtag lectureHashtag;

    private Boolean isOffline;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bookmark_id")
    private Bookmark bookmark;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecture_lecturer_id")
    private LectureLecturer lectureLecturer;

    private String lectureInfo;

    @OneToMany(mappedBy = "lecture")
    private List<Review> reviews = new ArrayList<>();

    private
}
