package com.cherrypick.backend.common.config;


import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;

//oauth2 정보를 가지고 있는 AppProperties configuration을 생성해준다.
//(인증 정보 redirectUri를 저장하고 가져올 수 있게 하는 역할)
@ConfigurationProperties(prefix = "app")
public class AppProperties {

  private final OAuth2 oauth2 = new OAuth2();

  public static final class OAuth2 {

    private List<String> authorizedRedirectUris = new ArrayList<>();

    public List<String> getAuthorizedRedirectUris() {
      return authorizedRedirectUris;
    }

    public OAuth2 authorizedRedirectUris(List<String> authorizedRedirectUris) {
      this.authorizedRedirectUris = authorizedRedirectUris;
      return this;
    }
  }

  public OAuth2 getOauth2() {
    return oauth2;
  }
}
