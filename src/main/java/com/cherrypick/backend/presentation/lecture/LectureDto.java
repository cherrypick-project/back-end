package com.cherrypick.backend.presentation.lecture;


import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

public class LectureDto {

  @Data
  @AllArgsConstructor
  public static class ConditionRequest {

    private String searchName;
    @JsonProperty("categoryId")
    private List<Long> categoryId;
    private Integer depth;
    private String providerId;
  }
}
