package com.application.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private final Logger log = LoggerFactory.getLogger(SwaggerConfig.class);

    @Value("${swagger.host}")
    private String swaggerHost;

    @Bean
    public Docket api() {
        log.debug("Starting Swagger on: {}", swaggerHost);
        StopWatch watch = new StopWatch();
        watch.start();
        Docket build = new Docket(DocumentationType.SWAGGER_2)
                .host(swaggerHost)
                .forCodeGeneration(true)
                .directModelSubstitute(java.nio.ByteBuffer.class, String.class)
                .genericModelSubstitutes(ResponseEntity.class)
                .select()
                .apis(RequestHandlerSelectors.any())
                .build();

        watch.stop();
        return build;
    }
}