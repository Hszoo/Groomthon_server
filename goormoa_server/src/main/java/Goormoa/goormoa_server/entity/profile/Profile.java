package Goormoa.goormoa_server.entity.profile;


import Goormoa.goormoa_server.entity.group.Group;
import Goormoa.goormoa_server.entity.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

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
    @JsonIgnore
    @JoinColumn(name = "user_id") // 또는 다른 적절한 컬럼 이름
    private User user;
//
//    private Long userId;
//    private String userNickName;

    private String profileImg; // 프로필 사진

    @ElementCollection
    private List<String> userInterests; // 유저 흥미 카테고리 -> 수정 예정

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "profile_group_mapping",
            joinColumns = @JoinColumn(name = "profile_id"), // 여기를 profile_id로 변경
            inverseJoinColumns = @JoinColumn(name = "group_id"))
    private List<Group> participatingGroups;


//    @OneToMany(mappedBy = "groupHost")
//    private List<Group> recruitingGroups = new ArrayList<>();



//    @ManyToMany(mappedBy = "applicants")
//    private List<Group> applyingGroups; // 프로필이 신청 중인 모임들


    //    // 생성자 정의
    public Profile(User user) {
        this.user = user;

    }


//
//
//    public Profile(Long userId) {
//        this.userId = userId;
//    }

}
