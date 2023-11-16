package Goormoa.goormoa_server.dto.profile;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDetailDTO {
    private Long userId;
    private String userEmail;
    private String userName;

    private Long profileId;
    private String profileImg;


}

