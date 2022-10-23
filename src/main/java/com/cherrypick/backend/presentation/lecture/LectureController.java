package com.cherrypick.backend.presentation.lecture;

import com.cherrypick.backend.application.LectureFacade;
import com.cherrypick.backend.common.response.CommonResponse;
import com.cherrypick.backend.domain.lecture.LectureCommand;
import com.cherrypick.backend.domain.lecture.LectureInfo.Lectures;
import com.cherrypick.backend.presentation.lecture.LectureDto.ConditionRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.security.Principal;
import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/v1")
@RequiredArgsConstructor
public class LectureController {

  private final LectureFacade lectureFacade;

  @Operation(
    summary = "강의 목록 조회",
    responses = @ApiResponse(responseCode = "200", description = "성공",
      content = @Content(array = @ArraySchema(schema = @Schema(implementation = Lectures.class, description = "강의 정보")))
    ))
  @GetMapping("/lectures")
  public ResponseEntity<CommonResponse> inquiryLectures(
    Principal principal,
    Pageable pageable,
    ConditionRequest request,
    @RequestParam(value = "isMobile", required = false, defaultValue = "false") Boolean isMobile
  ) {
    var command = request.toCommand(principal.getName(), request);
    var response = lectureFacade.inquiryLectures(command,
      pageable,
      isMobile);
    return ResponseEntity.ok(CommonResponse.success(response));
  }


  @Operation(summary = "강의 상세내용 조회", responses = {
    @ApiResponse(responseCode = "200", description = "성공",
      content = @Content(schema = @Schema(implementation = LectureDto.LectureDetail.class))
    )
  })
  @GetMapping("/lectures/{lectureId}")
  public ResponseEntity<CommonResponse> inquiryLectureDetail(
    Principal principal,
    @PathVariable("lectureId") Long lectureId) {
    var loginId = principal.getName();
    var response = lectureFacade.inquiryLectureDetail(loginId, lectureId);
    return ResponseEntity.ok(CommonResponse.success(response));
  }

  @Operation(summary = "강의 북마크 조회", responses = {
    @ApiResponse(responseCode = "200", description = "성공",
      content = @Content(schema = @Schema(implementation = Lectures.class))
    )
  })
  @PreAuthorize("hasAnyRole('ROLE_ADMIN') or hasAnyRole('ROLE_MEMBERSHIP')")
  @GetMapping("/lectures/bookmark")
  public ResponseEntity<CommonResponse> inquiryMyBookmark(
    Principal principal,
    Pageable pageable,
    @RequestParam(value = "isMobile", required = false, defaultValue = "false") Boolean isMobile
  ) {
    var loginId = principal.getName();
    var command = new LectureCommand.ConditionRequest(
      "",
      new ArrayList<>(),
      -1,
      loginId);

    var response = lectureFacade.inquiryLectures(command, pageable, isMobile);
    return ResponseEntity.ok(CommonResponse.success(response));
  }
}
