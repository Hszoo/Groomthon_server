package Goormoa.goormoa_server.config.web;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 모든 경로에 대하여
                .allowedOrigins("http://localhost:3000") // 리액트 앱의 URL을 허용 (개발 환경일 경우)
                .allowedMethods("GET", "POST", "PUT", "DELETE") // 허용할 HTTP 메소드 지정
                .allowedHeaders("*") // 모든 헤더 허용
                .allowCredentials(true) // 쿠키를 포함한 요청 허용
                .maxAge(3600); // pre-flight 요청의 결과를 1시간 동안 캐시
    }
}