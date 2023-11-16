package Goormoa.goormoa_server.service.profile;

import Goormoa.goormoa_server.dto.profile.ProfileDTO;
import Goormoa.goormoa_server.dto.profile.ProfileInfoDTO;
import Goormoa.goormoa_server.dto.user.UserInfoDTO;
import Goormoa.goormoa_server.entity.profile.Profile;
import Goormoa.goormoa_server.entity.user.User;
import Goormoa.goormoa_server.repository.profile.ProfileRepository;
import Goormoa.goormoa_server.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ProfileService {
    private final ProfileRepository profileRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    /* 프로필 GET 요청 처리 (완료) */
    public ProfileDTO getProfile(String currentUserEmail) {
        // controller에서 전달받은 user의 profile 정보 반환
        Optional<User> optionalUser = userRepository.findByUserEmail(currentUserEmail);

        if (optionalUser.isEmpty()) {
            return null;
        }
        User user = optionalUser.get();
        Profile profile = user.getProfile();

        if (profile != null) {
            ProfileInfoDTO profileInfo = new ProfileInfoDTO();
            UserInfoDTO userInfo = new UserInfoDTO(user);
            return ProfileDTO.toDTO(userInfo, profileInfo, profile);
        }
        throw new RuntimeException("Authentication error");
    }

    /* 프로필 수정 요청 처리 */
    public ProfileDTO editProfile(String userEmail, ProfileDTO updateProfileDTO) {
        Optional<User> loggedInUser = userRepository.findByUserEmail(userEmail);
        if(loggedInUser.isEmpty()) {
            return null;
        }

        User user = loggedInUser.get();
        Profile profile = profileRepository.findByUser(user);

        if (profile != null) {
            /* 프로필 업데이트 */
            ProfileInfoDTO profileDTO = updateProfileDTO.getProfileInfo();
            UserInfoDTO userDTO = updateProfileDTO.getUserInfo();
            profile.setProfileImg(profileDTO.getProfileImg());
            profile.setParticipatingGroups(profileDTO.getParticipatingGroups());
            profile.setUserInterests(profileDTO.getInterestings());


            return new ProfileDTO(updateProfileDTO.getUserInfo(), updateProfileDTO.getProfileInfo());
        } else {
            throw new NoSuchElementException("Profile not found");
        }
    }


//    /**/
//    public ProfileDTO saveProfile(ProfileDTO profileDto) {
//        // dto -> entity 전환
//        Profile profileEntity = modelMapper.map(profileDto, Profile.class);
//        logger.info("to entity : " + profileEntity.toString());
//
//        // 전환된 entity 저장
//        Profile saveEntity = profileRepository.save(profileEntity);
//        logger.info("save Entity : " + saveEntity.toString());
//
//        // entity -> dto
//        ProfileDTO dto = modelMapper.map(saveEntity, ProfileDTO.class);
//        logger.info("TO Dto : " + dto);
//
//        return dto;
//    }

    /* 엔터티 -> DTO 변환 */
    public ProfileDTO convertToProfileDTO(Profile profile) {
        if (profile == null) {
            return new ProfileDTO(); // 또는 다른 방법으로 처리
        }
        return modelMapper.map(profile, ProfileDTO.class);
    }

}

