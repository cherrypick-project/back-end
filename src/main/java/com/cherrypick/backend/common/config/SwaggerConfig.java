package com.cherrypick.backend.common.config;

import com.fasterxml.classmate.TypeResolver;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseBuilder;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Response;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@RequiredArgsConstructor
public class SwaggerConfig implements WebMvcConfigurer {

  private final TypeResolver typeResolver;

  @Override
  public void addResourceHandlers(final ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/swagger-ui/**")
      .addResourceLocations("classpath:/META-INF/resources/webjars/springfox-swagger-ui/");
    registry
      .addResourceHandler("/webjars/**")
      .addResourceLocations("classpath:/META-INF/resources/webjars/springfox-swagger-ui/");
  }

  @Bean
  public Docket api() {
    List<Response> commonResponse = setCommonResponse();
    return new Docket(DocumentationType.SWAGGER_2)
      .useDefaultResponseMessages(false)
      .globalResponses(HttpMethod.GET, commonResponse)
      .globalResponses(HttpMethod.POST, commonResponse)
      .globalResponses(HttpMethod.PUT, commonResponse)
      .globalResponses(HttpMethod.PATCH, commonResponse)
      .globalResponses(HttpMethod.DELETE, commonResponse)
      .alternateTypeRules(AlternateTypeRules
        .newRule(typeResolver.resolve(Pageable.class), typeResolver.resolve(Page.class)))
      .consumes(getConsumeContentTypes())
      .produces(getProduceContentTypes())
      .apiInfo(apiInfo())
      .select()
      .apis(RequestHandlerSelectors.basePackage("com.cherrypick.backend"))
      .paths(PathSelectors.regex("/api/v1/.*"))
      .build()
      .securityContexts(Arrays.asList(securityContext()))
      .securitySchemes(Arrays.asList(apiKey()));
  }

  private List<Response> setCommonResponse() {
    List<Response> list = new ArrayList<>();
    list.add(new ResponseBuilder().code("200").description("정상 처리(성공)").build());
    list.add(new ResponseBuilder().code("401").description("토큰 만료 또는 비정상 토큰 또는 권한 없음")
      .build());
    list.add(new ResponseBuilder().code("404").description("존재하지 않는 api 요청 ").build());
    list.add(new ResponseBuilder().code("500").description("내부 서버 오류(문의 필요)").build());
    return list;
  }

  /* jwt 설정 */
  private ApiKey apiKey() {
    return new ApiKey("JWT", "Authorization", "header");
  }

  private SecurityContext securityContext() {
    return SecurityContext
      .builder()
      .securityReferences(defaultAuth()).forPaths(PathSelectors.any()).build();
  }

  List<SecurityReference> defaultAuth() {
    AuthorizationScope authorizationScope = new AuthorizationScope("global",
      "accessEverything");
    AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
    authorizationScopes[0] = authorizationScope;
    return Arrays.asList(new SecurityReference("JWT", authorizationScopes));
  }
  /* jwt 설정 */

  private Set<String> getConsumeContentTypes() {
    Set<String> consumes = new HashSet<>();
    consumes.add("application/json;charset=UTF-8");
    consumes.add("application/x-www-form-urlencoded");
    return consumes;
  }

  private Set<String> getProduceContentTypes() {
    Set<String> produces = new HashSet<>();
    produces.add("application/json;charset=UTF-8");
    return produces;
  }

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder()
      .title("Sig-Predict REST API Document")
      .description("work in progress")
      .termsOfServiceUrl("localhost")
      .version("1.0")
      .build();
  }

  @Getter
  @Setter
  @ApiModel
  static class Page {

    @ApiModelProperty(value = "페이지 번호(0..N)")
    private Integer page;

    @ApiModelProperty(value = "페이지 크기", allowableValues = "range[0, 100]")
    private Integer size;

    @ApiModelProperty(value = "정렬(사용법: 컬럼명,ASC|DESC)")
    private List<String> sort;
  }
}
