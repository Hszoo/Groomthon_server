package Goormoa.goormoa_server.entity.profile;


import Goormoa.goormoa_server.entity.user.User;
import lombok.*;

import javax.persistence.*;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="profile_table")
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long profileId; // 프로필 고유 식별자

    /* user - profile 매핑 */
    @OneToOne
    @JoinColumn(name = "user_id", unique = true, referencedColumnName = "userId")
    private User user; // 1 대 1 유저

//    private String userId;
//    private String userNickName;

    private String profileImg; // 프로필 사진

//    @ElementCollection
//    private List<String> userInterests; // 유저 흥미 카테고리 -> 수정 예정

//    @ManyToMany
//    @JoinTable(
//            name = "profile_participating_groups",
//            joinColumns = @JoinColumn(name = "profile_id"),
//            inverseJoinColumns = @JoinColumn(name = "group_id")
//    )
//    private List<Group> participatingGroups; // 프로필이 참여 중인 모임들
//
//    @ManyToMany(mappedBy = "applicants")
//    private List<Group> applyingGroups; // 프로필이 신청 중인 모임들
//
//
//
//    @OneToMany(mappedBy = "groupHost")
//    private List<Group> recruitingGroups; // 프로필이 모집 중인 모임들


    // 생성자 정의
    public Profile(User user, String profileImg) {
        this.user = user;
        this.profileImg = profileImg;
    }

//    public static Profile toEntity(ProfileDTO profileDto) {
//        return Profile.builder()
////                .user(profileDto.getUserId())
////                .userNickName(profileDto.getUserName())
////                .userId(profileDto.getUserId())
////                .userInterests(profileDto.getUserInterests())
//                .profileImg(profileDto.getProfileImg())
//                .build();
//    }

    public Profile(User user) {
        this.user = user;
    }

}

