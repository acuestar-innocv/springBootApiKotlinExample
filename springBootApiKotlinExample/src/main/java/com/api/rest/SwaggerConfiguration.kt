package com.api.rest

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.AuthorizationScopeBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.*
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spi.service.contexts.SecurityContext
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2
import java.util.*

@Configuration
@EnableSwagger2
class SwaggerConfiguration {

    @Bean
    fun api(): Docket = Docket(DocumentationType.SWAGGER_2)
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.api.rest.controller"))
            .paths(PathSelectors.any())
            .build()
            .apiInfo(metaData())
            .securitySchemes(securitySchemes())
            .securityContexts(securityContexts())


    private fun metaData(): ApiInfo = ApiInfoBuilder()
            .title("Example Kotlin API")
            .build()

    private fun securityContexts(): List<SecurityContext> {
        val authScopes = arrayOfNulls<AuthorizationScope>(1)
        authScopes[0] = AuthorizationScopeBuilder()
                .scope("ADMIN_ROLE")
                .build()
        val securityReference = SecurityReference.builder()
                .reference("api")
                .scopes(authScopes)
                .build()
        val securityContext = SecurityContext.builder()
                .securityReferences(Collections.singletonList(securityReference))
                .build()

        return Collections.singletonList(securityContext)
    }

    private fun securitySchemes(): List<SecurityScheme> {
        return Collections.singletonList(BasicAuth("api"))
    }
}
