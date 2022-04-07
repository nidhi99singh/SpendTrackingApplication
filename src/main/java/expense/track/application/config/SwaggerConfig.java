package expense.track.application.config;

import expense.track.application.enums.SwaggerConstant;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .securityContexts(Arrays.asList(securityContext()))
                .securitySchemes(Arrays.asList(apiKey()))
                .select()
                .apis(RequestHandlerSelectors.basePackage("expense.track.application.controller"))
                .paths(PathSelectors.ant("/**"))
                .build()
                .apiInfo(apiInfo())
                .securitySchemes(Collections.singletonList(apiKey()))
                .securityContexts(Collections.singletonList(securityContext()))
                .useDefaultResponseMessages(false);
    }

//    @Bean
//    public Docket SwaggerConfig()
//    {
//        return new Docket(DocumentationType.SWAGGER_2).select().paths(PathSelectors.ant("/**"))
//                .apis(RequestHandlerSelectors.basePackage("expense.track.application.controller")).build().apiInfo(apiDetails());
//    }

    private ApiKey apiKey() {
        return new ApiKey(SwaggerConstant.JWT.getValue(), SwaggerConstant.X_AUTHORIZATION.getValue(), SwaggerConstant.HEADER.getValue());
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(defaultAuth()).build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope(SwaggerConstant.GLOBAL.getValue(), SwaggerConstant.ACCESS_EVERYTHING.getValue());
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Arrays.asList(new SecurityReference(SwaggerConstant.JWT.getValue(), (springfox.documentation.service.AuthorizationScope[]) authorizationScopes));
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("Flex83 API Documentation")
                .description("These are API's")
                .license("IOT83")
                .version("1.0")
                .build();
    }
//
//    @Bean
//    public Docket api() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .groupName("FLEX83")
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("expense.track.application.controller"))
//                .paths(PathSelectors.ant("/api/**"))
//                .build()
//                .apiInfo(apiInfo())
//                .securitySchemes(Collections.singletonList(apiKey()))
//                .securityContexts(Collections.singletonList(securityContext()))
//                .useDefaultResponseMessages(false);
//    }


}
