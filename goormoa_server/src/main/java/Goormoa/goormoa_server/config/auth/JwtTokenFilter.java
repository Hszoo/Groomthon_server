package Goormoa.goormoa_server.config.auth;


import Goormoa.goormoa_server.service.token.TokenService;
import Goormoa.goormoa_server.service.user.UserService;
import Goormoa.goormoa_server.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class JwtTokenFilter extends OncePerRequestFilter {

    private final UserService userService;
    private final String secretKey;
    private final TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws SecurityException, ServletException, IOException {

        final String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        log.info("authorization : {}", authorization);

        // 토큰이 없다면 막는다
        if(authorization == null || !authorization.startsWith("Bearer ")) {
            log.error("authorization 오류");
            filterChain.doFilter(request, response);
            return;
        }

        // 토큰 꺼내기
        String token = authorization.split(" ")[1];

        // 블랙리스트에 토큰이 있는지 확인
        if (tokenService.isTokenBlacklisted(token)) {
            log.error("Blacklisted token");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Blacklisted token");
            return;
        }

        // 토큰 만료 시간 확인
        if (JwtUtil.isExpired(token, secretKey)) {
            log.error("Token이 만료 되었습니다.");
            filterChain.doFilter(request, response);
            return;
        }

        // userEmail Token 에서 꺼내기
        String userEmail = JwtUtil.getUserEmail(token, secretKey);
        log.info("userEmail:{}",userEmail);

        // 권한 부여
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userEmail, null, List.of(new SimpleGrantedAuthority("USER")));
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);
    }
}
