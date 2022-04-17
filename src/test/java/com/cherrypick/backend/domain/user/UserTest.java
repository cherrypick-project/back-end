package com.cherrypick.backend.domain.user;

import static com.cherrypick.backend.domain.user.UserCommand.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.cherrypick.backend.domain.user.User.Career;
import com.cherrypick.backend.security.oauth.ProviderType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserTest {

  @DisplayName("입력받은 추가정보를 회원정보에 추가시킨다.")
  @Test
  void addUserInfo() {
    User user = User.OauthSignUp("1", "test1234@gmail.com",
        "1234", ProviderType.GOOGLE);
    SignUpRequest command = new SignUpRequest("1", "BackEnd", Career.LESS_THAN_3YEARS, "Search");

    user.addUserInfo(command);

    assertThat(user.getJob()).isEqualTo(command.getJob());
    assertThat(user.getCareer()).isEqualTo(command.getCareer());
    assertThat(user.getKnownPath()).isEqualTo(command.getKnownPath());
  }
}