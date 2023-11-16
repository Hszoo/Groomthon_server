package Goormoa.goormoa_server.controller.profile;

import Goormoa.goormoa_server.dto.profile.ProfileDTO;
import Goormoa.goormoa_server.repository.profile.ProfileRepository;
import Goormoa.goormoa_server.repository.user.UserRepository;
import Goormoa.goormoa_server.service.auth.AuthenticationService;
import Goormoa.goormoa_server.service.profile.ProfileService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor // final로 선언한 변수에 대해 생성자 작성해줌 (반복 코드 최소화)
@RestController
public class ProfileController {
    /* service */
    private final ProfileService profileService;
    private final AuthenticationService authenticationService;

    /* repository */
    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(ProfileController.class);

    /* 프로필 GET 요청 */
    @GetMapping("/profile")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ProfileDTO getProfile() {
        logger.info("프로필 컨트롤러 동작중");

        String currentUserEmail = authenticationService.getCurrentAuthenticatedUserEmail();
        return profileService.getProfile(currentUserEmail);
    }

    /* post 요청 : 회원가입시에만 발생 */
//    @PostMapping("/profile")
//    @ResponseStatus(HttpStatus.CREATED)
//    public ProfileDTO createProfile(@RequestBody ProfileDTO requestDto) {
//        String currentUserEmail = authenticationService.getCurrentAuthenticatedUserEmail();
//        Optional<User> optionalUser = userRepository.findByUserEmail(currentUserEmail);
//
//        if(!optionalUser.isPresent()) {
//            return null;
//        }
//
//        User user = optionalUser.get();
//        Profile profile = profileRepository.findByUser(user);
//
//        ProfileDTO profileDto = profileService.createProfile(requestDto, user);
//        if (profileDto != null) {
//            return profileDto;
//        }
//        /* 사용자가 인증되지 않은 경우 */
//        return null;
//    }

    /* 프로필 편집 */

    /* 마이페이지 GET 요청 */
//    @GetMapping("/mypage")
//    @PatchMapping("/profile")
//    public ProfileDTO editProfile(@RequestBody ProfileDTO editRequestDto) {
//        logger.info("프로필 컨트롤러 -> 프로필 수정 동작중");
//
//        String currentUserEmail = authenticationService.getCurrentAuthenticatedUserEmail();
//        return profileService.editProfile(currentUserEmail, editRequestDto);
//
//        String currentUserEmail = authenticationService.getCurrentAuthenticatedUserEmail();
//        Optional<User> optionalUser = userRepository.findByUserEmail(currentUserEmail);
//
//        if(!optionalUser.isPresent()) {
//            return null;
//        }
//
//        User user = optionalUser.get();
////        Profile profile = profileRepository.findByUser(user);
//
//        ProfileDTO profileDto = profileService.editProfile(editRequestDto, user);
//        if (profileDto != null) {
//            return profileDto;
//        }
//        /* 사용자가 인증되지 않은 경우 */
//        return null;
//   }
//    @ResponseBody
//    @ResponseStatus(HttpStatus.OK)
//    public MypageDTO getMyPage() {
//        String currentUserEmail = authenticationService.getCurrentAuthenticatedUserEmail();
//        Optional<User> optionalUser = userRepository.findByUserEmail(currentUserEmail);
//
//        if(!optionalUser.isPresent()) {
//            return null;
//        }
//
//        User user = optionalUser.get();
//        Profile profile = profileRepository.findByUser(user);
//
////        MypageDTO mypageDto = profileService.getMypage(user);
//        if (mypageDto != null) {
//            return mypageDto;
//        }
//        /* 사용자가 인증되지 않은 경우 */
//        return null;
//    }

//
//    /* 내 모임 조회 get 요청 */
//    @GetMapping("/mypage/participating-group/")
//    @ResponseBody
//    @ResponseStatus(HttpStatus.OK)
//    public MyGroupDTO getMyGroups() {
//        String currentUserEmail = authenticationService.getCurrentAuthenticatedUserEmail();
//        Optional<User> optionalUser = userRepository.findByUserEmail(currentUserEmail);
//
//        if(!optionalUser.isPresent()) {
//            return null;
//        }
//
//        User user = optionalUser.get();
//
//        // 그룹 멤버에서 해당 사용자가 가입 중인 모든 모임 보여주도록
////        Profile myProfile = profileRepository.findByUser(user);
//        List<Group> myGroups = myProfile.getParticipatingGroups();
//        List<Group> myRGroups = myProfile.getRecruitingGroups();
//        MyGroupDTO myGroupDto = new MyGroupDTO();
//
//        myGroupDto.setParticipatingGroups(myGroups);
//        myGroupDto.setRecruitingGroups(myRGroups);
//
//        return myGroupDto;
//    }

}

