package com.cherrypick.backend.presentation.user;

import com.cherrypick.backend.domain.user.UserInfo;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface UserDtoMapper {

  UserDto.ProfileResponse of (UserInfo.Profile profile);

  UserDto.SignOutResponse of (UserInfo.SignOut profile);

}
