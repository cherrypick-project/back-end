package com.cherrypick.backend.domain.lecture;

import com.cherrypick.backend.domain.lecturer.Lecturer;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access =  AccessLevel.PROTECTED)
@RequiredArgsConstructor
public class LectureLecturer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "lectureLecturer")
    private List<Lecture> lectures = new ArrayList<>();

    @OneToMany(mappedBy = "lectureLecturer")
    private List<Lecturer> lecturers = new ArrayList<>();
}
