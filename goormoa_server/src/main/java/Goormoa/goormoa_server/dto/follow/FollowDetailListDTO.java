package Goormoa.goormoa_server.dto.follow;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
public class FollowDetailListDTO {
    private Long profileId;
    private String profileImg;

    public FollowDetailListDTO(Long profileId, String profileImg) {
        this.profileId = profileId;
        this.profileImg = profileImg;
    }
}

