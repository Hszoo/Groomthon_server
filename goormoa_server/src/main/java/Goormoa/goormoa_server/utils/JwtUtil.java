package Goormoa.goormoa_server.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    // JWT 토큰에서 "userEmail" 정보를 추출하는 메소드
    public static String getUserEmail(String token, String secretKey) {
        return Jwts.parser()
                .setSigningKey(secretKey) // 비밀키를 설정하여 JWT 서명 검증
                .parseClaimsJws(token) // 토큰을 파싱하여 Claims(클레임) 객체로 반환
                .getBody() // Claims 객체의 내용(Body)을 가져옴
                .get("userEmail", String.class); // "userEmail"에 해당하는 값을 String 타입으로 추출
    }

    // 토큰의 유효 기간이 만료되었는지 확인하는 메소드
    public static boolean isExpired(String token, String secretKey) {
        // 토큰의 만료 시간을 가져와 현재 시간과 비교
        return Jwts.parser()
                .setSigningKey(secretKey) // 비밀키를 설정
                .parseClaimsJws(token) // 토큰을 파싱
                .getBody() // Claims 객체의 내용(Body)을 가져옴
                .getExpiration() // 토큰의 만료 시간을 가져옴
                .before(new Date()); // 만료 시간이 현재 시간보다 이전인지 확인
    }

    // 새로운 JWT 토큰을 생성하는 메소드
    public static String createJwt(String userEmail, String secretKey, Long expiredMs) {
        Claims claims = Jwts.claims(); // 클레임을 담을 객체 생성
        claims.put("userEmail", userEmail); // 클레임에 "userEmail"을 저장

        // JWT 토큰을 생성하여 반환
        return Jwts.builder()
                .setClaims(claims) // 클레임(여기서는 유저 이름) 설정
                .setIssuedAt(new Date(System.currentTimeMillis())) // 토큰 발행 시간 설정
                .setExpiration(new Date(System.currentTimeMillis() + expiredMs)) // 토큰 만료 시간 설정
                .signWith(SignatureAlgorithm.HS256, secretKey) // HS256 알고리즘과 비밀키를 사용하여 서명
                .compact(); // JWT 토큰을 문자열로 압축하여 반환
    }
}
