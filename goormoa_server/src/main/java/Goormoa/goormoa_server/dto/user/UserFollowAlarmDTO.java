package Goormoa.goormoa_server.dto.user;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserFollowAlarmDTO {
    private Long userId;
    private String userName;
}
