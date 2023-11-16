package Goormoa.goormoa_server.dto.follow;


import Goormoa.goormoa_server.dto.user.UserFollowAlarmDTO;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class FollowDTO {
    private UserFollowAlarmDTO toUser;
    private UserFollowAlarmDTO fromUser;

    public FollowDTO(UserFollowAlarmDTO toUser, UserFollowAlarmDTO fromUser) {
        this.toUser = toUser;
        this.fromUser = fromUser;
    }
}
