package com.cherrypick.backend.domain.lecture;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class LectureCommand {

  @Getter
  @AllArgsConstructor
  public static class ConditionRequest {
    private String searchName;
    private List<Long> categoryId;
    private int depth;
    private String providerId;
  }
}
