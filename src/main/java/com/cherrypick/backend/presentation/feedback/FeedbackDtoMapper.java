package com.cherrypick.backend.presentation.feedback;

import com.cherrypick.backend.domain.feedback.FeedbackCommand;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
  componentModel = "spring",
  injectionStrategy = InjectionStrategy.CONSTRUCTOR,
  unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface FeedbackDtoMapper {

  FeedbackCommand.RegisterFeedbackRequest of(FeedbackDto.RegisterFeedbackRequest request);
}
