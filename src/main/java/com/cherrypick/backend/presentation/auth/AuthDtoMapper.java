package com.cherrypick.backend.presentation.auth;


import com.cherrypick.backend.domain.user.UserCommand;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
  componentModel = "spring",
  injectionStrategy = InjectionStrategy.CONSTRUCTOR,
  unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface AuthDtoMapper {

  UserCommand.UserLoginRequest of(AuthDto.LoginRequest request);
}
