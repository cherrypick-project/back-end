package com.cherrypick.backend.domain.review;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.cherrypick.backend.common.exception.BusinessException;
import com.cherrypick.backend.domain.review.Review.CostPerformance;
import com.cherrypick.backend.domain.review.Review.Recommendation;
import com.cherrypick.backend.domain.review.Review.Status;
import com.cherrypick.backend.domain.review.ReviewCommand.RegisterRequest;
import com.cherrypick.backend.domain.review.ReviewInfo.ReviewDetail;
import com.cherrypick.backend.domain.user.User;
import com.cherrypick.backend.domain.user.User.Career;
import com.cherrypick.backend.infrastructure.lecture.LectureReaderImpl;
import com.cherrypick.backend.infrastructure.review.ReviewReaderImpl;
import com.cherrypick.backend.infrastructure.user.UserReaderImpl;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ReviewServiceImplTest {

  @InjectMocks
  private ReviewServiceImpl reviewService;

  @Mock
  private LectureReaderImpl lectureReader;
  @Mock
  private UserReaderImpl userReader;
  @Mock
  private ReviewReaderImpl reviewReader;
  @Mock
  private PreviewReviewIdGenerator previewReviewIdGenerator;

  @Test
  @DisplayName("Review 생성시 사용자를 찾지 못하면 예외발생")
  void notFoundUser() {
    RegisterRequest command = createRequest();

    given(userReader.findByProviderId(any())).willReturn(Optional.empty());

    assertThatThrownBy(() -> {
      reviewService.createReview(command);
    }).isInstanceOf(BusinessException.class);
  }

  @Test
  @DisplayName("Review 생성시 강의를 찾지 못하면 예외발생")
  void notFoundLecture() {
    RegisterRequest command = createRequest();

    given(userReader.findByProviderId(any())).willReturn(Optional.of(createUser()));
    given(lectureReader.findByLectureId(any())).willReturn(Optional.empty());

    assertThatThrownBy(() -> {
      reviewService.createReview(command);
    }).isInstanceOf(BusinessException.class);
  }

  @Test
  @DisplayName("Review가 없으면 예외발생")
  void notFoundReview() {
    given(reviewReader.findMaxId()).willReturn(Optional.empty());

    assertThatThrownBy(() -> {
      reviewService.inquiryPreviewReviews();
    }).isInstanceOf(BusinessException.class);
  }

  @Test
  @DisplayName("중복되지 않는 3개의 미리보기 리뷰 조회")
  void inquiryNonDuplicationPreviewReviews() {
    given(reviewReader.findMaxId()).willReturn(Optional.of(3L));
    given(reviewReader.findAllPreviewReviewInIds(List.of(1L, 2L, 3L))).willReturn(
      createEnoughReviews());
    given(previewReviewIdGenerator.createNonDuplicationRandomIds(3L)).willReturn(
      List.of(1L, 2L, 3L));
    List<ReviewDetail> previewReviews = reviewService.inquiryPreviewReviews();

    assertThat(previewReviews).hasSize(3);
    assertThat(previewReviews).doesNotHaveDuplicates();
  }

  @Test
  @DisplayName("리뷰 총 갯수가 3보다 작으면 중복이 포함된 3개의 미리보기 리뷰 조회")
  void inquiryDuplicationPreviewReviews() {
    given(reviewReader.findMaxId()).willReturn(Optional.of(2L));
    given(reviewReader.findAllPreviewReviewInIds(List.of(1L, 2L))).willReturn(
      createNonEnoughReviews());
    given(previewReviewIdGenerator.createSequentialIds(2L)).willReturn(
      List.of(1L, 2L));
    List<ReviewDetail> previewReviews = reviewService.inquiryPreviewReviews();

    assertThat(previewReviews).hasSize(3);
  }

  private ReviewCommand.RegisterRequest createRequest() {
    return new RegisterRequest("user", 1L, 2.5, Recommendation.GOOD, CostPerformance.MIDDLE, "test",
      "test", "test");
  }

  private User createUser() {
    return User.testUser(1L, "eeeee@gmail.com");
  }

  private List<ReviewDetail> createEnoughReviews() {
    return List.of(
      new ReviewDetail(1L, 3.5, Recommendation.GOOD, CostPerformance.MIDDLE, "좋아여", "굿", "마이크 소리작음",
        Status.APPROVE, 1L, "백엔드", Career.LESS_THAN_3YEARS),
      new ReviewDetail(2L, 2.5, Recommendation.GOOD, CostPerformance.MIDDLE, "별로", "쏘쏘", "마이크 소리작음",
        Status.APPROVE, 1L, "프론트엔드", Career.LESS_THAN_3YEARS),
      new ReviewDetail(3L, 1.5, Recommendation.BAD, CostPerformance.MIDDLE, "듣지마세여", "내용부실",
        "마이크 소리작음", Status.APPROVE, 1L, "백엔드", Career.LESS_THAN_3YEARS)
    );
  }

  private List<ReviewDetail> createNonEnoughReviews() {
    return List.of(
      new ReviewDetail(1L, 3.5, Recommendation.GOOD, CostPerformance.MIDDLE, "좋아여", "굿", "마이크 소리작음",
        Status.APPROVE, 1L, "백엔드", Career.LESS_THAN_3YEARS),
      new ReviewDetail(2L, 2.5, Recommendation.GOOD, CostPerformance.MIDDLE, "별로", "쏘쏘", "마이크 소리작음",
        Status.APPROVE, 1L, "프론트엔드", Career.LESS_THAN_3YEARS)
    );
  }
}