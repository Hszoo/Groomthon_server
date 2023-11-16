package Goormoa.goormoa_server.dto.profile;


import Goormoa.goormoa_server.entity.profile.Profile;
import Goormoa.goormoa_server.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProfileCreateRequestDTO {
    private User user;
    private String profileImg;
    private List<String> userInterestedList;
    public Profile toDto(ProfileCreateRequestDTO req, User user) {
        return Profile.builder()
                .user(req.user)
                .profileImg(req.profileImg)
//                .userInterests(req.userInterestedList)
                .build();
    }
}