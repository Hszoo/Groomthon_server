package Goormoa.goormoa_server.dto.profile;

import Goormoa.goormoa_server.entity.profile.Profile;
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
}

