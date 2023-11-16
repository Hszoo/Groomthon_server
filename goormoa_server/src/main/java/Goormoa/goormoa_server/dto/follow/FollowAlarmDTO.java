package Goormoa.goormoa_server.dto.follow;

import Goormoa.goormoa_server.entity.user.User;
import lombok.*;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FollowAlarmDTO {
    private Long followId;
    private User toUser;
    private User fromUser;
}
