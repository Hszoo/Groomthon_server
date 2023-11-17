package Goormoa.goormoa_server.dto.profile;

import lombok.*;
import Goormoa.goormoa_server.dto.user.UserInfoDTO;
import Goormoa.goormoa_server.entity.profile.Profile;

import java.util.List;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor

public class ProfileDTO {
    private Long profileId; // 프로필 고유 식별자
    private UserInfoDTO userInfo;
    private ProfileInfoDTO profileInfo;

    public ProfileDTO(UserInfoDTO userInfo, ProfileInfoDTO profileInfo) {
        this.userInfo = userInfo;
        this.profileInfo = profileInfo;
    }
    
    public static ProfileDTO toDTO(UserInfoDTO userInfo, ProfileInfoDTO profileInfo, Profile profile) {
        ProfileDTO dto = new ProfileDTO();
        dto.setProfileId(profile.getProfileId());
        dto.setProfileInfo(profileInfo);
        dto.setUserInfo(userInfo);
        return dto;
    }
}
