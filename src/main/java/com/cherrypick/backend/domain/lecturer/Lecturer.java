package com.cherrypick.backend.domain.lecturer;

import com.cherrypick.backend.domain.lecture.LectureLecturer;
import lombok.*;

import javax.persistence.*;


@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RequiredArgsConstructor
public class Lecturer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecture_lecturer_id")
    private LectureLecturer lectureLecturer;

}
