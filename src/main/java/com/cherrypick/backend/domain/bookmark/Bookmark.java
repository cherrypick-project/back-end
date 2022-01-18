package com.cherrypick.backend.domain.bookmark;

import com.cherrypick.backend.domain.lecture.Lecture;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@RequiredArgsConstructor
@NoArgsConstructor(
        access = AccessLevel.PROTECTED
)
public class Bookmark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "bookmark" , cascade = CascadeType.ALL)
    private List<Lecture> lectures = new ArrayList<>();

}
