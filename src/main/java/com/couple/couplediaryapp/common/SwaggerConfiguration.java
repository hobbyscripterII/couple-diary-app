package com.couple.couplediaryapp.common;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration // 스프링 설정 어노테이션
public class SwaggerConfiguration {
    @Bean // Bean 등록
    public OpenAPI openAPI() {
        Info info = new Info()
                .title("찌리릿 VER.1")
                .description("커플 다이어리 APP Ver.1")
                .version("1.0.0");

        return new OpenAPI()
                .components(new Components())
                .info(info);
    }
}
