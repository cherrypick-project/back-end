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

  @Bean
  public HttpCookieOAuth2AuthorizationRequestRepository cookieAuthorizationRequestRepository() {
    return new HttpCookieOAuth2AuthorizationRequestRepository();
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
      // token??? ???????????? ???????????? ????????? csrf??? disable?????????.
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

      // ????????? ???????????? ?????? ????????? STATELESS??? ??????
      .and()
      .addFilter(corsFilter)
      .sessionManagement()
      .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

      .and()
      .authorizeRequests() // HttpServletRequest??? ???????????? ???????????? ?????? ??????????????? ??????
      .antMatchers(
        "/user/v1/auth/authenticate",
        "/user/v1/auth/reissue",
        "/swagger-ui/**",
        "/swagger-resources/**",
        "/v2/api-docs",
        "/webjars/**"
      ).permitAll()
      .antMatchers(
        "/oauth2/**"
      ).permitAll()
      .anyRequest().authenticated() // ????????? ???????????? ????????? ????????????

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

