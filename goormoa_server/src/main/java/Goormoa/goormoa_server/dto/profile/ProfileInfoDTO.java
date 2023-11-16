package Goormoa.goormoa_server.dto.profile;


import Goormoa.goormoa_server.entity.group.Group;
import Goormoa.goormoa_server.entity.profile.Profile;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProfileInfoDTO {
    private String profileImg; // 프로필 사진
    private List<Group> participatingGroups;
    private List<String> interestings;
    public ProfileInfoDTO(Profile profile) {
        this.profileImg = profile.getProfileImg();
        this.participatingGroups = profile.getParticipatingGroups();
        this.interestings = profile.getUserInterests();
    }

    // 생성자 정의
//    public static MypageDTO toDTO(Profile profile)
//        return MypageDTO.builder()
//                .user(profile.getUser())
//                .userId(profile.getUserId())
//                .userNickName(profile.getUserNickName())
//                .userInterests(profile.getUserInterests())
//                .profileImg(profile.getProfileImg())
//                .recruitingGroups(profile.getRecruitingGroups())
//                .participatingGroups(profile.getParticipatingGroups())
//                .build();


}
