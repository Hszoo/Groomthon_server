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
public class ProfileDTO {
    private Long userId;
    private String userEmail;
    private String userName;
    private Long profileId;
    private String profileImg; // 프로필 사진

    public ProfileDTO(User user, Profile profile) {
        this.userId = user.getUserId();
        this.userEmail = user.getUserEmail();
        this.userName = user.getUserName();
        this.profileId = profile.getProfileId();
        this.profileImg = profile.getProfileImg();
    }
}
