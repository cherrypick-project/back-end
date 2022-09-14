package com.cherrypick.backend.domain.review;

import static javax.persistence.FetchType.LAZY;

import com.cherrypick.backend.domain.BaseEntity;
import com.cherrypick.backend.domain.lecture.Lecture;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.Locale;
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
public class Review extends BaseEntity {

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

  @Enumerated(EnumType.STRING)
  private Status status;

  private Long userId;

  private Review(Lecture lecture, double rating,
    Recommendation recommendation,
    CostPerformance costPerformance, String oneLineComment, String strengthComment,
    String weaknessComment, Long userId) {

    this.lecture = lecture;
    this.rating = rating;
    this.recommendation = recommendation;
    this.costPerformance = costPerformance;
    this.oneLineComment = oneLineComment;
    this.strengthComment = strengthComment;
    this.weaknessComment = weaknessComment;
    this.userId = userId;
    this.status = Status.READY;
  }

  public static Review of(Lecture lecture, double rating,
    Recommendation recommendation,
    CostPerformance costPerformance, String oneLineComment, String strengthComment,
    String weaknessComment, Long userId) {
    return new Review(lecture, rating, recommendation, costPerformance, oneLineComment,
      strengthComment,
      weaknessComment, userId);
  }

  public void approve() {
    this.status = Status.APPROVE;
  }

  @Getter
  public enum Recommendation {
    GOOD("GOOD", "추천해요"),
    BAD("BAD", "별로예요");
    private final String code;
    private final String desc;

    Recommendation(String code, String desc) {
      this.code = code;
      this.desc = desc;
    }

    @JsonCreator
    public static Recommendation from(String s) {
      return Recommendation.valueOf(s.toUpperCase(Locale.ROOT));
    }
  }

  @Getter
  public enum CostPerformance {
    VERY_SATISFACTION("VERY_SATISFACTION", "매우 만족"),
    SATISFACTION("SATISFACTION", "만족"),
    MIDDLE("MIDDLE", "보통"),
    SOSO("SOSO", "그저 그럼");

    private final String code;
    private final String desc;

    CostPerformance(String code, String desc) {
      this.code = code;
      this.desc = desc;
    }

    @JsonCreator
    public static CostPerformance from(String s) {
      return CostPerformance.valueOf(s.toUpperCase(Locale.ROOT));
    }
  }

  @Getter
  public enum Status {
    READY("READY", "대기"), APPROVE("APPROVE", "승인"), REJECT("REJECT", "서정");

    private final String code;
    private final String desc;

    Status(String code, String desc) {
      this.code = code;
      this.desc = desc;
    }
  }
}
