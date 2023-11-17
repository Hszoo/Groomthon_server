package Goormoa.goormoa_server.dto.recommend;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RecommendFriendDTO {
    private Long userId;
    private String userEmail;
    private String userName;
    private String profileImg;
}
