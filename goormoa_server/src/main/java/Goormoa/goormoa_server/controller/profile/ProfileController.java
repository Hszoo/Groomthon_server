package Goormoa.goormoa_server.controller.profile;

import Goormoa.goormoa_server.dto.profile.ProfileDTO;
import Goormoa.goormoa_server.entity.user.User;
import Goormoa.goormoa_server.repository.profile.ProfileRepository;
import Goormoa.goormoa_server.repository.user.UserRepository;
import Goormoa.goormoa_server.service.auth.AuthenticationService;
import Goormoa.goormoa_server.service.profile.ProfileService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequiredArgsConstructor // final로 선언한 변수에 대해 생성자 작성해줌 (반복 코드 최소화)
@RestController
public class ProfileController {
    private final ProfileService profileService;
    private final AuthenticationService authenticationService;
    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(ProfileController.class);

    /* 프로필 GET 요청 */
    @GetMapping("/profile")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ProfileDTO getProfile() {
        logger.info("프로필 컨트롤러 -> 프로필 조회중");
        String currentUserEmail = authenticationService.getCurrentAuthenticatedUserEmail();
        return profileService.getProfile(currentUserEmail);
    }

    /* 다른 사용자 프로필 GET 요청 */
    @GetMapping("/profile/{userId}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ProfileDTO getOtherProfile(@PathVariable Long userId) {
        logger.info("프로필 컨트롤러 -> 다른 사용자 프로필 조회중");
        Optional<User> optionalUser = userRepository.findByUserId(userId);
        if(!optionalUser.isPresent()) {
            return null;
        }
        User user = optionalUser.get();
        return profileService.getProfile(user.getUserEmail());
    }

    /* 프로필 편집 */
    @PostMapping("/profile/update")
    public String update(@RequestBody ProfileDTO editRequestDto) {
        logger.info("프로필 컨트롤러 -> 프로필 편집중");

        String currentUserEmail = authenticationService.getCurrentAuthenticatedUserEmail();
        return profileService.update(currentUserEmail, editRequestDto);
    }
}



