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
        User user = userRepository.findByUserEmail(currentUserEmail).orElse(null);
        if(user == null)
            throw new RuntimeException("null error");
        Profile profile = user.getProfile();

        if (profile != null) {
            return new ProfileDTO(user, profile);
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
            profile.setProfileImg(updateProfileDTO.getProfileImg());
            System.out.println("error1");
            profile.setCategory(updateProfileDTO.getCategory());
            System.out.println("error2");
            profileRepository.save(profile);
            return "프로필 업데이트가 완료되었습니다.";
        }
        return "error";
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
}
