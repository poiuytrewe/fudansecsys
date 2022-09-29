package com.fudanuniversity.cms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * Created by Xinyue.Tang at 2021-05-08 15:52:53
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.OAS_30)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.fudanuniversity.cms.controller"))
                .paths(PathSelectors.any())
                .build();
    }
}
