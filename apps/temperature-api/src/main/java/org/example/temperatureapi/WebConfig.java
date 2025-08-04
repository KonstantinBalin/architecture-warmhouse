package org.example.temperatureapi;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")  // для всех путей
                    .allowedOrigins("*") // разрешить все домены
                    .allowedMethods("*") // разрешить все методы
                    .allowedHeaders("*") // разрешить все заголовки
                    .allowCredentials(false); // без передачи куки
            }
        };
    }
}
