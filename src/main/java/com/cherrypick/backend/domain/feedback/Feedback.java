package com.cherrypick.backend.domain.feedback;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Builder
@RequiredArgsConstructor
@NoArgsConstructor(
        access = AccessLevel.PROTECTED
)
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int rating;

    private String content;
}
