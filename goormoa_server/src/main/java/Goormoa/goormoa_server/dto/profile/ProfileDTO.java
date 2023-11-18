package Goormoa.goormoa_server.dto.profile;

import Goormoa.goormoa_server.entity.user.User;
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
    private Long userId;
    private String userEmail;
    private String userName;
    private Long profileId; // 프로필 고유 식별자
    private String profileImg; // 프로필 사진
    private List<String> category;

    public ProfileDTO(User user, Profile profile) {
        this.userId = user.getUserId();
        this.userEmail = user.getUserEmail();
        this.userName = user.getUserName();
        this.profileId = profile.getProfileId();
        this.profileImg = profile.getProfileImg();
        this.category = profile.getCategory();
    }

//    public static ProfileDTO toDTO(UserInfoDTO userInfo, ProfileInfoDTO profileInfo, Profile profile) {
//        ProfileDTO dto = new ProfileDTO();
//        dto.setProfileId(profile.getProfileId());
//        dto.setProfileInfo(profileInfo);
//        dto.setUserInfo(userInfo);
//        return dto;
//    }
}