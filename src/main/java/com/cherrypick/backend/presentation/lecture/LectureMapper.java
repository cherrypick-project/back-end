package com.cherrypick.backend.presentation.lecture;

import com.cherrypick.backend.domain.lecture.LectureCommand.CreateLectureCommand;
import com.cherrypick.backend.presentation.lecture.LectureDto.CreateLectureRequest;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
  componentModel = "spring",
  injectionStrategy = InjectionStrategy.CONSTRUCTOR,
  unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface LectureMapper {

  CreateLectureCommand of(CreateLectureRequest request);

}
