package Goormoa.goormoa_server.dto.profile;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor

public class ProfileDTO {
    private Long profileId; // 프로필 고유 식별자
    private ProfileInfoDTO profileInfo;

    /* 생성자 */

}
