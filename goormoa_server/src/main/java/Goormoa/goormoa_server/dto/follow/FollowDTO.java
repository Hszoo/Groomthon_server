package Goormoa.goormoa_server.dto.follow;


import Goormoa.goormoa_server.dto.profile.ProfileFollowListDTO;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FollowDTO {
    private Long userId;
    private String userName;
    private ProfileFollowListDTO profileFollowListDTO;
}
