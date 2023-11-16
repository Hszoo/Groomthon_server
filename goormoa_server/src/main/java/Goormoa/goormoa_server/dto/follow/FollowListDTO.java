package Goormoa.goormoa_server.dto.follow;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class FollowListDTO {
    private Long profileId;
    private String profileImg;

}

