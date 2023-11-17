package Goormoa.goormoa_server.entity.profile;

import Goormoa.goormoa_server.entity.user.User;
import Goormoa.goormoa_server.entity.group.Group;
import lombok.*;
import java.util.List;
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

    private String profileImg; // 프로필 사진

    @ElementCollection
    private List<String> userInterests; // 유저 흥미 카테고리 -> 수정 예정

    @ManyToMany
    @JoinTable(
            name = "profile_participating_groups",
            joinColumns = @JoinColumn(name = "profile_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id")
    )
    private List<Group> participatingGroups;

    public Profile(User user) {
        this.user = user;
    }

}

