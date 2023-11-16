package Goormoa.goormoa_server.dto.follow;


import Goormoa.goormoa_server.dto.user.UserFollowAlarmDTO;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class FollowDTO {
    private Long followId;
    private UserFollowAlarmDTO toUser; // 상대 유저
    private UserFollowAlarmDTO fromUser; // 현재 유저

    public FollowDTO(Long followId, UserFollowAlarmDTO toUser, UserFollowAlarmDTO fromUser) {
        this.followId = followId;
        this.toUser = toUser;
        this.fromUser = fromUser;
    }
}
