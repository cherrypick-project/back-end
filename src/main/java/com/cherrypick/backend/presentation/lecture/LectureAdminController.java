package com.cherrypick.backend.presentation.lecture;

import com.cherrypick.backend.application.LectureFacade;
import com.cherrypick.backend.common.response.CommonResponse;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/v1")
@RequiredArgsConstructor
public class LectureAdminController {

  private final LectureFacade lectureFacade;
  private final LectureMapper lectureMapper;

  @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
  @PostMapping("/lectures")
  public ResponseEntity<CommonResponse> createLecture(
    @RequestBody @Valid LectureDto.CreateLectureRequest request) {
    var command = lectureMapper.of(request);
    lectureFacade.createLecture(command);
    return ResponseEntity.ok(CommonResponse.success());
  }

  @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
  @PatchMapping("/lectures/{lectureId}")
  public ResponseEntity<CommonResponse> updateLecture(
    @PathVariable Long lectureId,
    @RequestBody @Valid LectureDto.UpdateLectureRequest request
  ) {
    var command = request.toCommand(lectureId);
    lectureFacade.updateLecture(command);
    return ResponseEntity.ok(CommonResponse.success());
  }
}
