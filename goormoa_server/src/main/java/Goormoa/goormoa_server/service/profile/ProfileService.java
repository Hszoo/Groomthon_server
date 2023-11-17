package Goormoa.goormoa_server.service.profile;

import Goormoa.goormoa_server.dto.group.GroupDTO;
import Goormoa.goormoa_server.dto.profile.ProfileDTO;
import Goormoa.goormoa_server.dto.profile.ProfileInfoDTO;
import Goormoa.goormoa_server.dto.user.UserInfoDTO;
import Goormoa.goormoa_server.entity.group.Group;
import Goormoa.goormoa_server.entity.profile.Profile;
import Goormoa.goormoa_server.entity.user.User;
import Goormoa.goormoa_server.repository.group.GroupRepository;
import Goormoa.goormoa_server.repository.profile.ProfileRepository;
import Goormoa.goormoa_server.repository.user.UserRepository;
import Goormoa.goormoa_server.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ProfileService {
    private final ProfileRepository profileRepository;
    private final GroupRepository groupRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    /* 프로필 GET 요청 처리 (완료) */
    public ProfileDTO getProfile(String currentUserEmail) {
        // controller에서 전달받은 user의 profile 정보 반환
        User user = userRepository.findByUserEmail(currentUserEmail).orElse(null);
        if(user == null)
            throw new RuntimeException("null error");
        Profile profile = user.getProfile();
        System.out.println("[getProfile]");
        System.out.println(profile.getProfileImg());

        if (profile != null) {
            ProfileInfoDTO profileInfo = new ProfileInfoDTO(profile);
            UserInfoDTO userInfo = new UserInfoDTO(user);
            System.out.println("[error 1]");
            return ProfileDTO.toDTO(userInfo, profileInfo, profile);
        }
        throw new RuntimeException("Authentication error");
    }

    /* 프로필 수정 요청 처리 */
    public String update(String currentUserEmail, ProfileDTO updateProfileDTO) {
        User user = userRepository.findByUserEmail(currentUserEmail).orElse(null);
        if(user == null)
            throw new RuntimeException("null error");

        Profile profile = getByProfile(updateProfileDTO);
        if (profile != null) {
            profile.setProfileImg(updateProfileDTO.getProfileInfo().getProfileImg());
//            profile.setCategory(updateProfileDTO.getProfileInfo().getCategory());
//            updateProfileDetails(profile, user, updateProfileDTO);
            profileRepository.save(profile);
            return "프로필 업데이트가 완료되었습니다.";
        }
        return "error";
    }


//        Profile profile = getByProfile(updateProfileDTO);
//
//        if (profile != null) {
//            // 중복된 프로필이 이미 존재하면 새로운 프로필을 생성
//            Profile updateProfile = new Profile();
//            updateProfile.setProfileImg(updateProfileDTO.getProfileInfo().getProfileImg());
//            updateProfile.setCategory(updateProfileDTO.getProfileInfo().getCategory());
//            updateProfile.setUser(userOptional.get());
//
//            profileRepository.save(updateProfile);
//            return "프로필 업데이트가 완료되었습니다.";
//        }
//
//        return "error";
//    }



//        if(user == null)
//            throw new RuntimeException("null error");
//
//        Profile profile = getByProfile(updateProfileDTO);
//        if (profile != null) {
//            Long updateUserId= updateProfileDTO.getUserInfo().getUserId();
//            User updateUser = userRepository.findByUserId(updateUserId).get();
//            if(updateUser != null) {
//                System.out.println(updateUser.getUserName());
//                userRepository.save(updateUser);
//            }

//            profile.setProfileImg(updateProfileDTO.getProfileInfo().getProfileImg());
//            System.out.println(updateProfileDTO.getProfileInfo().getProfileImg());
//            profile.setCategory(updateProfileDTO.getProfileInfo().getCategory());
//            System.out.println(updateProfileDTO.getProfileInfo().getCategory());
//            updateProfileDetails(profile, user, updateProfileDTO);
//            profileRepository.save(profile);
//            return "프로필 업데이트가 완료되었습니다.";

//        return "error";
//    }


    private void updateProfileDetails(Profile profile, User user, ProfileDTO profileDTO) {
//        user.setUserEmail(profileDTO.getUserInfo().getUserEmail());
        user.setUserName(profileDTO.getUserInfo().getUserName());
        profile.setProfileImg(profileDTO.getProfileInfo().getProfileImg());
//        profile.setCategory(profileDTO.getProfileInfo().getCategory());
        profileRepository.save(profile);
        userRepository.save(user);
    }
    public ProfileDTO editProfile(String userEmail, ProfileDTO updateProfileDTO) {
        Optional<User> loggedInUser = userRepository.findByUserEmail(userEmail);
        if (loggedInUser.isEmpty()) {
            return null;
        }

        User user = loggedInUser.get();
        Profile profile = profileRepository.findByUser(user);

        if (profile != null) {
            /* 프로필 업데이트 */
            ProfileInfoDTO updatedProfileInfoDTO = updateProfileDTO.getProfileInfo();

            profile.setProfileImg(updatedProfileInfoDTO.getProfileImg());
//            profile.setCategory(updatedProfileInfoDTO.getCategory());

            // sdsd
            profileRepository.save(profile);
            return new ProfileDTO(updateProfileDTO.getUserInfo(), updatedProfileInfoDTO);
        } else {
            throw new NoSuchElementException("Profile not found");
        }
    }


    private void updateProfileFields(Profile profile, ProfileInfoDTO updatedProfileInfoDTO) {
        profile.setProfileImg(updatedProfileInfoDTO.getProfileImg());
//        profile.setCategory(updatedProfileInfoDTO.getCategory());
    }

    /* 엔터티 -> DTO 변환 */
    public ProfileDTO convertToProfileDTO(Profile profile) {
        if (profile == null) {
            return new ProfileDTO(); // 또는 다른 방법으로 처리
        }
        return modelMapper.map(profile, ProfileDTO.class);
    }

    private Profile getByProfile(ProfileDTO profileDTO) {
        return profileRepository.findById(profileDTO.getProfileId()).orElse(null);
    }

//    private User getByUser(String currentUserEmail) {
//        return
//    }
}
                         