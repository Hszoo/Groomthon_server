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
}