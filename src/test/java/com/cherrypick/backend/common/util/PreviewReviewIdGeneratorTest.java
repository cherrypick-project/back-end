package com.cherrypick.backend.common.util;

import static org.assertj.core.api.Assertions.assertThat;

import com.cherrypick.backend.domain.review.PreviewReviewIdGenerator;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PreviewReviewIdGeneratorTest {

  @DisplayName("3개의 중복되지 않은 랜덤 ID를 생성")
  @Test
  void createNonDuplicationRandomIds() {
    PreviewReviewIdGenerator randomIdGenerator = new PreviewReviewIdGenerator();
    List<Long> randomIds = randomIdGenerator.createNonDuplicationRandomIds(3L);

    assertThat(randomIds).doesNotHaveDuplicates();
    assertThat(randomIds).hasSize(3);
  }

  @DisplayName("3개의 중복 랜덤 ID를 생성")
  @Test
  void createDuplicationRandomIds() {
    PreviewReviewIdGenerator randomIdGenerator = new PreviewReviewIdGenerator();
    List<Long> randomIds = randomIdGenerator.createSequentialIds(2L);

    assertThat(randomIds).hasSize(3);
  }
}