package Goormoa.goormoa_server.dto.user;

import Goormoa.goormoa_server.entity.user.User;
import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailDTO {
    /* 사용자 정보, 프로필 정보 보여주기 위한 dto */
    private Long userId;
    private String userEmail;
    private String userName;
    private Long profileId;
    private String profileImg;

    public UserDetailDTO(User user) {
        this.userId = user.getUserId();
        this.userName = user.getUserName();
        this.userEmail = user.getUserEmail();

        // Profile이 null이 아닌 경우에만 초기화
        if (user.getProfile() != null) {
            this.profileId = user.getProfile().getProfileId();
            this.profileImg = user.getProfile().getProfileImg();
        }
    }
}