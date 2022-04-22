package com.cherrypick.backend.common.config;

import com.cherrypick.backend.common.jwt.JwtAccessDeniedHandler;
import com.cherrypick.backend.common.jwt.JwtAuthenticationEntryPoint;
import com.cherrypick.backend.common.jwt.TokenProvider;
import com.cherrypick.backend.common.oauth.OAuth2AuthenticationFailureHandler;
import com.cherrypick.backend.common.oauth.OAuth2SuccessHandler;
import com.cherrypick.backend.domain.user.oauth.CustomOauth2UserService;
import com.cherrypick.backend.domain.user.oauth.HttpCookieOAuth2AuthorizationRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.filter.CorsFilter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableConfigurationProperties(AppProperties.class)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private final TokenProvider tokenProvider;
  private final CorsFilter corsFilter;
  private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
  private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
  private final CustomOauth2UserService customOauth2UserService;
  private final OAuth2SuccessHandler oAuth2SuccessHandler;
  private final OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;
  private final HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;

  @Bean
  public HttpCookieOAuth2AuthorizationRequestRepository cookieAuthorizationRequestRepository() {
    return new HttpCookieOAuth2AuthorizationRequestRepository();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  public void configure(WebSecurity web) {
    web.ignoring()
      .antMatchers(
        "/h2-console/**"
        , "/favicon.ico"
        , "/error"
      );
  }

  @Override
  protected void configure(HttpSecurity httpSecurity) throws Exception {
    httpSecurity
      // token을 사용하는 방식이기 때문에 csrf를 disable합니다.
      .csrf()
      .disable()
      .formLogin()
      .disable()
      .exceptionHandling()
      .authenticationEntryPoint(jwtAuthenticationEntryPoint)
      .accessDeniedHandler(jwtAccessDeniedHandler)

      // enable h2-console
      .and()
      .headers()
      .frameOptions()
      .sameOrigin()

      // 세션을 사용하지 않기 때문에 STATELESS로 설정
      .and()
      .addFilter(corsFilter)
      .sessionManagement()
      .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

      .and()
      .authorizeRequests() // HttpServletRequest를 사용하는 요청들에 대한 접근제한을 설정
      .antMatchers(
        "/api/v1/auth/authenticate", "/api/v1/auth/reissue"
      ).permitAll()
      .antMatchers(
        "/oauth2/**"
      ).permitAll()
      .anyRequest().authenticated() // 나머지 요청들은 인증을 받아야됨

      .and()
      .oauth2Login()
      .authorizationEndpoint()
      .baseUri("/oauth2/authorize")
      .authorizationRequestRepository(cookieAuthorizationRequestRepository())
      .and()
      .redirectionEndpoint()
      .baseUri("/oauth2/callback/*")
      .and()
      .userInfoEndpoint()
      .userService(customOauth2UserService)
      .and()
      .successHandler(oAuth2SuccessHandler)
      .failureHandler(oAuth2AuthenticationFailureHandler)
      .and()
      .apply(new JwtSecurityConfig(tokenProvider));
  }
}

