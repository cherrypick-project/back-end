package com.cherrypick.backend.domain.review;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PreviewReviewIdGenerator {

  protected static final int PREVIEW_REVIEW_COUNT = 3;
  private static final int RANDOM_NUMBER_ORIGIN = 0;

  public List<Long> createNonDuplicationRandomIds(Long maxId) {
    List<Long> reviewIds = ThreadLocalRandom.current()
      .longs(PREVIEW_REVIEW_COUNT, RANDOM_NUMBER_ORIGIN, maxId)
      .distinct()
      .boxed().collect(Collectors.toList());

    while (isEnough(reviewIds)) {
      long reviewId = ThreadLocalRandom.current().nextLong(RANDOM_NUMBER_ORIGIN, maxId);
      if (!isDuplicate(reviewId, reviewIds)) {
        reviewIds.add(reviewId);
      }
    }
    return reviewIds;
  }

  private boolean isEnough(List<Long> reviewIds) {
    return reviewIds.size() < PREVIEW_REVIEW_COUNT;
  }

  private boolean isDuplicate(long reviewId, List<Long> reviewIds) {
    return reviewIds.contains(reviewId);
  }

  public List<Long> createSequentialIds(Long maxId) {
    return LongStream.rangeClosed(0, maxId)
      .boxed()
      .collect(Collectors.toList());
  }
}
