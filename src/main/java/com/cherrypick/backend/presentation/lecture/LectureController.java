package com.cherrypick.backend.presentation.lecture;

import com.cherrypick.backend.application.LectureFacade;
import com.cherrypick.backend.common.response.CommonResponse;
import com.cherrypick.backend.domain.lecture.LectureInfo;
import com.cherrypick.backend.presentation.lecture.LectureDto.ConditionRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/v1")
@RequiredArgsConstructor
public class LectureController {

  private final LectureFacade lectureFacade;
  private final LectureDtoMapper lectureDtoMapper;

  @GetMapping("/lectures")
  public ResponseEntity<CommonResponse> inquiryLectures(@AuthenticationPrincipal UserDetails user,
    Pageable pageable, ConditionRequest request) {
    request.setProviderId(user.getUsername());
    var command = lectureDtoMapper.of(request);
    var response = lectureFacade.inquiryLectures(command, pageable);
    return ResponseEntity.ok(CommonResponse.success(response));
  }

  @GetMapping("/lectures/{lectureId}")
  public ResponseEntity<CommonResponse> inquiryLectureDetail(
    @AuthenticationPrincipal UserDetails user,
    @PathVariable("lectureId") Long lectureId) {
    var loginId = user.getUsername();
    var lectureInfo = lectureFacade.inquiryLectureDetail(loginId, lectureId);
    return ResponseEntity.ok(CommonResponse.success(lectureInfo));
  }
}
