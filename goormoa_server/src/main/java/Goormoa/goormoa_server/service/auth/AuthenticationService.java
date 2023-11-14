package Goormoa.goormoa_server.service.auth;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    public String getCurrentAuthenticatedUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            if ("anonymousUser".equals(authentication.getPrincipal())) {
                return null;
            }
            return authentication.getName();
        }
        return null;
    }
}
