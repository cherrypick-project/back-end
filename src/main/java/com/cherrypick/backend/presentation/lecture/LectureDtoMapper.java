package com.cherrypick.backend.presentation.lecture;

import com.cherrypick.backend.domain.lecture.LectureCommand.ConditionRequest;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface LectureDtoMapper {

  ConditionRequest of(LectureDto.ConditionRequest request);
}
