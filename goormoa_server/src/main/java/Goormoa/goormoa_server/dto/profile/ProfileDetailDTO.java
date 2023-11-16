package Goormoa.goormoa_server.dto.profile;

import Goormoa.goormoa_server.entity.profile.Profile;
import Goormoa.goormoa_server.entity.user.User;
import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDetailDTO {
    private Long userId;
    private String userEmail;
    private String userName;

    private Long profileId;
    private String profileImg;

    public ProfileDetailDTO(Profile profile) {
        this.profileId = profile.getProfileId();
        this.profileImg = profile.getProfileImg();
    }

    public static ProfileDetailDTO toDTO(User user, Profile profile) {
        ProfileDetailDTO dto = new ProfileDetailDTO();
        dto.setUserId(user.getUserId());
        dto.setUserName(user.getUserName());
        dto.setUserEmail(user.getUserEmail());
        dto.setProfileId(profile.getProfileId());
        dto.setProfileImg(profile.getProfileImg());
        return dto;
    }
}

