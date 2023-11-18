package Goormoa.goormoa_server.dto.follow;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FollowListDTO {
    private Long userId;
    private String userEmail;
    private String userName;
    private FollowDetailListDTO followDetailListDTO;
}