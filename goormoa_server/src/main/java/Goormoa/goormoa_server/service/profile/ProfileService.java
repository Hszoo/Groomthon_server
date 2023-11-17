package Goormoa.goormoa_server.service.profile;

import Goormoa.goormoa_server.dto.profile.ProfileDTO;
import Goormoa.goormoa_server.entity.profile.Profile;
import Goormoa.goormoa_server.entity.user.User;
import Goormoa.goormoa_server.repository.profile.ProfileRepository;
import Goormoa.goormoa_server.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProfileService {
    private final ProfileRepository profileRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    /* 프로필 조회 요청 처리 */
    public ProfileDTO getProfile(String currentUserEmail) {
        // controller에서 전달받은 user의 profile 정보 반환
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
            profileRepository.save(profile);
            return "프로필 업데이트가 완료되었습니다.";
        }
        return "error";
    }

    private Profile getByProfile(ProfileDTO profileDTO) {
        return profileRepository.findById(profileDTO.getProfileId()).orElse(null);
    }
}
                         