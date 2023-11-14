package Goormoa.goormoa_server.dto.profile;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProfileFollowListDTO {
    private Long profileId;
    private String profileImg;

}
