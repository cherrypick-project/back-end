package com.cherrypick.backend.common.oauth;

import com.cherrypick.backend.common.util.CookieUtils;
import com.cherrypick.backend.domain.user.oauth.HttpCookieOAuth2AuthorizationRequestRepository;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

  private final HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;

  @Override
  public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
    AuthenticationException exception) throws IOException, ServletException {
    super.onAuthenticationFailure(request, response, exception);

    String targetUrl = CookieUtils.getCookie(request,
        HttpCookieOAuth2AuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME)
      .map(Cookie::getValue)
      .orElse(("/"));
    targetUrl = UriComponentsBuilder.fromUriString(targetUrl)
      .queryParam("error", exception.getLocalizedMessage())
      .build().toUriString();
    httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request,
      response);
    getRedirectStrategy().sendRedirect(request, response, targetUrl);
  }
}
