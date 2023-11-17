package Goormoa.goormoa_server.dto.user;

import Goormoa.goormoa_server.entity.user.User;
import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDTO {
    private Long userId;
    private String userEmail;
    private String userName;
    public UserInfoDTO(User user) {
        this.userId = user.getUserId();
        this.userName = user.getUserName();
        this.userEmail = user.getUserEmail();
    }
}