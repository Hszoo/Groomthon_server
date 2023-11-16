package Goormoa.goormoa_server.config.auth;


import Goormoa.goormoa_server.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import Goormoa.goormoa_server.service.token.TokenService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration // 이 클래스를 스프링 설정 클래스로 등록
@EnableWebSecurity // 이 클래스에 웹 보안 설정을 활성화
@RequiredArgsConstructor // final 또는 @NonNull 필드 값을 파라미터로 받는 생성자를 생성 (lombok 라이브러리의 어노테이션)
public class AuthenticationConfig {
    private final UserService userService; // 유저 서비스 의존성 주입
    private final TokenService tokenService;

    @Value(("${jwt.secret}")) // application.properties 또는 yml 파일의 jwt.secret 값을 여기에 주입
    private String secretKey; // JWT 토큰 생성 및 파싱에 사용할 비밀키

    @Bean // 스프링 컨테이너에 등록할 Bean 객체를 정의
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .httpBasic().disable() // 기본 로그인 페이지 비활성화 (토큰 인증을 사용하기 때문)
                .csrf().disable() // CSRF(Cross-Site Request Forgery) 보호 기능 비활성화
                .cors().and() // CORS(Cross-Origin Resource Sharing) 설정 활성화
                .authorizeRequests() // HttpServletRequest를 사용한 접근 제한 설정
                .antMatchers("/login", "/signup", "/", "/chat/**", "/ws/**").permitAll() // /login, /signup 경로는 인증 없이 접근 허용
                .antMatchers(HttpMethod.POST, "/**").authenticated() // 모든 POST 요청은 인증이 필요함
                .antMatchers(HttpMethod.GET, "/**").authenticated() // 모든 GET 요청도 인증이 필요
                .and()
                .sessionManagement() // 세션 관리
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 세션을 사용하지 않고 STATELESS로 설정
                .and()
                .addFilterBefore(new JwtTokenFilter(userService, secretKey, tokenService), UsernamePasswordAuthenticationFilter.class)
                // JwtTokenFilter를 UsernamePasswordAuthenticationFilter 전에 추가
                .build(); // SecurityFilterChain 객체 생성 및 반환
    }
}
