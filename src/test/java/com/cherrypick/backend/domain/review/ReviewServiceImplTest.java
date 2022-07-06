package com.cherrypick.backend.domain.review;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.cherrypick.backend.common.exception.BusinessException;
import com.cherrypick.backend.domain.review.Review.CostPerformance;
import com.cherrypick.backend.domain.review.Review.Recommendation;
import com.cherrypick.backend.domain.review.ReviewCommand.RegisterRequest;
import com.cherrypick.backend.domain.user.User;
import com.cherrypick.backend.infrastructure.lecture.LectureReaderImpl;
import com.cherrypick.backend.infrastructure.user.UserReaderImpl;
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

  @Test
  @DisplayName("Review 생성시 강의나 사용자를 찾지 못하면 예외발생")
  void NOT_FOUND_USER() {
    RegisterRequest command = createRequest();

    given(userReader.findByProviderId(any())).willReturn(Optional.empty());

    assertThatThrownBy(() -> {
      reviewService.createReview(command);
    }).isInstanceOf(BusinessException.class);
  }

  @Test
  @DisplayName("Review 생성시 강의나 사용자를 찾지 못하면 예외발생")
  void NOT_FOUND_LECTURE() {
    RegisterRequest command = createRequest();

    given(userReader.findByProviderId(any())).willReturn(Optional.of(createUser()));
    given(lectureReader.findByLectureId(any())).willReturn(Optional.empty());

    assertThatThrownBy(() -> {
      reviewService.createReview(command);
    }).isInstanceOf(BusinessException.class);
  }

  private ReviewCommand.RegisterRequest createRequest() {
    return new RegisterRequest("user", 1L, 2.5, Recommendation.GOOD, CostPerformance.MIDDLE, "test",
      "test", "test");
  }

  private User createUser() {
    return User.testUser(1L, "eeeee@gmail.com");
  }
}