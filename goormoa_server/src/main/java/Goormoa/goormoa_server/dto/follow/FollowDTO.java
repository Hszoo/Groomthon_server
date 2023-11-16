package Goormoa.goormoa_server.dto.follow;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FollowDTO {
    private Long userId;
    private String userName;
    private FollowListDTO followListDTO;
}
