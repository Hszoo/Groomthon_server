package Goormoa.goormoa_server.dto.profile;

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
    private List<String> interestings;


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
