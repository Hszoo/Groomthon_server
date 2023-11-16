package Goormoa.goormoa_server.dto.user;


import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
public class MyPageUserDTO {
    private Long userId;
    private String userName;
//    private List<String> userInterests; // 유저 흥미 카테고리
//    private String profileImg; // 프로필 사진
//    private List<Group> recruitingGroups;
//    private List<Group> participatingGroups;

//    // 생성자 정의
//    public static com.example.goormoa.dto.profile.MypageDTO toDTO(Profile profile) {
//        return com.example.goormoa.dto.profile.MypageDTO.builder()
//                .user(profile.getUser())
////                .userId(profile.getUserId())
////                .userNickName(profile.getUserNickName())
////                .userInterests(profile.getUserInterests())
//                .profileImg(profile.getProfileImg())
////                .recruitingGroups(profile.getRecruitingGroups())
////                .participatingGroups(profile.getParticipatingGroups())
//                .build();
//    }
//
//    public static MyPageUserDTO toDTO(User user) {
//        return MyPageUserDTO.builder()
//                .userId(user.getUserId())
//                .userName(user.getUserName())
//                .build();
//    }


    public MyPageUserDTO(Long userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }
}
