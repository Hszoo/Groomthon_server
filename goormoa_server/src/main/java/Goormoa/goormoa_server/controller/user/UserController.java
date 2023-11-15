package Goormoa.goormoa_server.controller.user;


import Goormoa.goormoa_server.dto.token.TokenDTO;
import Goormoa.goormoa_server.dto.user.UserDTO;
import Goormoa.goormoa_server.service.auth.AuthenticationService;
import Goormoa.goormoa_server.service.token.TokenService;
import Goormoa.goormoa_server.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final TokenService tokenService;
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody UserDTO userDto) {
        TokenDTO jwt = userService.login(userDto);
        return jwt != null ?
                ResponseEntity.ok(jwt) :
                ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody UserDTO userDto) {
        String signupResult = userService.save(userDto);
        return "error".equals(signupResult) ?
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body("존재하는 이메일 입니다.") :
                ResponseEntity.ok(signupResult);
    }

    @PostMapping("/out")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        return tokenService.extractToken(request.getHeader("Authorization"))
                .map(token -> {
                    tokenService.blacklistToken(token);
                    return ResponseEntity.ok("로그아웃 성공");
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("토큰을 찾을 수 없습니다."));
    }

    @PostMapping("/delete")
    public ResponseEntity<String> delete() {
        String currentUserEmail = authenticationService.getCurrentAuthenticatedUserEmail();
        String deleteResult = userService.delete(currentUserEmail);
        return "error".equals(deleteResult) ?
                ResponseEntity.status(HttpStatus.NOT_FOUND).body("유저를 찾을 수 없습니다.") :
                ResponseEntity.ok("회원탈퇴 완료");
    }
    @GetMapping("/api/getUserEmail")
    public ResponseEntity<String> getUserEmail() {
        String currentUserEmail = authenticationService.getCurrentAuthenticatedUserEmail();
        return ResponseEntity.ok(currentUserEmail);
    }
}
