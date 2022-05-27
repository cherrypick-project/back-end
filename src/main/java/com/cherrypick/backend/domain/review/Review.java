package com.cherrypick.backend.domain.review;

import static javax.persistence.FetchType.LAZY;

import com.cherrypick.backend.domain.lecture.Lecture;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
public class Review {

  @Id
  @Column(name = "id", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "lecture_id")
  private Lecture lecture;

  private double rating;

  @Enumerated(EnumType.STRING)
  private Recommendation recommendation;

  @Enumerated(EnumType.STRING)
  private CostPerformance costPerformance;

  private String oneLineComment;

  @Column(length = 500)
  private String strengthComment;

  @Column(length = 500)
  private String weaknessComment;

  private Status status;

  public enum Recommendation {
    GOOD, BAD
  }

  public enum CostPerformance {
    VERY_SATISFACTION, SATISFACTION, MIDDLE, SOSO
  }

  public enum Status {
    READY, APPROVE, REJECT
  }
}
