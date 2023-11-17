package Goormoa.goormoa_server.service.token;

import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TokenService {
    private Set<String> blacklistedTokens = ConcurrentHashMap.newKeySet();
    public void blacklistToken(String token) {
        blacklistedTokens.add(token);
    }
    public boolean isTokenBlacklisted(String token) {
        return blacklistedTokens.contains(token);
    }

    public Optional<String> extractToken(String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return Optional.of(authHeader.substring(7));
        }
        return Optional.empty();
    }

    public String logout(String authHeader) {
        Optional<String> optionalToken = extractToken(authHeader);
        if (optionalToken.isPresent()) {
            String token = optionalToken.get();
            blacklistToken(token);
            return "로그아웃 성공";
        } else {
            return "error";
        }
    }
}