package kr.co.fastcampus.fastcatch.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedOrigins("http://localhost:8080", "http://localhost:8081",
                "https://fast-catch-front-end-umh7.vercel.app", "http://localhost:5173",
                "http://58.127.37.16:5173",
                "http://localhost:5174",
                "http://localhost:5175",
                "http://localhost:5176",
                "http://localhost:5177")
            .allowedMethods("GET", "POST", "PUT", "DELETE")
            .allowedHeaders("Origin", "X-Requested-With", "Content-Type", "Accept", "Authorization")
            .allowCredentials(true)
            .maxAge(3000);
    }
}
