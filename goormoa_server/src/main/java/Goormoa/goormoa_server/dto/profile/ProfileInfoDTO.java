package Goormoa.goormoa_server.dto.profile;

import Goormoa.goormoa_server.entity.category.Category;
import Goormoa.goormoa_server.entity.profile.Profile;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ProfileInfoDTO {
    private String profileImg; // 프로필 사진
    private List<String> category;

    public ProfileInfoDTO(Profile profile) {
        this.profileImg = profile.getProfileImg();
        this.category = profile.getCategory();
    }
}

