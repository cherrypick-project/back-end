package com.cherrypick.backend.common.config;

import com.cherrypick.backend.common.jwt.JwtAccessDeniedHandler;
import com.cherrypick.backend.common.jwt.JwtAuthenticationEntryPoint;
import com.cherrypick.backend.common.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private final TokenProvider tokenProvider;
  private final CorsFilter corsFilter;
  private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
  private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

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
      .csrf().disable()

      .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)

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
      .sessionManagement()
      .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

      .and()
      .authorizeRequests() // HttpServletRequest를 사용하는 요청들에 대한 접근제한을 설정
      .antMatchers(
        "/api/v1/auth/login"
      ).permitAll()

      .anyRequest().authenticated() // 나머지 요청들은 인증을 받아야됨

      .and()
      .apply(new JwtSecurityConfig(tokenProvider));
  }
}

