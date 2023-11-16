package Goormoa.goormoa_server.dto.profile;


import Goormoa.goormoa_server.entity.user.User;
import lombok.*;

import java.util.List;
import Goormoa.goormoa_server.entity.group.Group;
import Goormoa.goormoa_server.entity.profile.Profile;
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MypageDTO {
    private User user; // 1 대 1 유저
    private String userId;
    private String userNickName;
    private List<String> userInterests; // 유저 흥미 카테고리
    private String profileImg; // 프로필 사진
    private List<Group> recruitingGroups;
    private List<Group> participatingGroups;

    // 생성자 정의
    public static MypageDTO toDTO(Profile profile) {
        return MypageDTO.builder()
                .user(profile.getUser())
//                .userId(profile.getUserId())
//                .userNickName(profile.getUserNickName())
//                .userInterests(profile.getUserInterests())
                .profileImg(profile.getProfileImg())
//                .recruitingGroups(profile.getRecruitingGroups())
//                .participatingGroups(profile.getParticipatingGroups())
                .build();
    }

}
