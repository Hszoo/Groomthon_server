package Goormoa.goormoa_server.entity.user;

import Goormoa.goormoa_server.entity.follow.Follow;
import Goormoa.goormoa_server.entity.group.GroupMember;
import Goormoa.goormoa_server.entity.profile.Profile;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="user_table")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId; // 유저 고유 식별자

    @Column(nullable = false, length = 50)
    private String userName; // 유저 이름

    @Column(nullable = false, unique = true, length = 100)
    private String userEmail; // 유저 이메일

    @Column(nullable = false, length = 100)
    private String userPassword; // 유저 비밀번호

    @OneToOne(cascade = CascadeType.ALL)
    private Profile profile; // Profile 엔티티와의 일대일 관계 정의

    @OneToMany(mappedBy = "fromUser", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Follow> fromUsers; // 나를 팔로우하는 사용자 목록 (팔로워)

    @OneToMany(mappedBy = "toUser", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Follow> toUsers; // 내가 팔로우하는 사용자 목록 (팔로잉)

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GroupMember> groupMembers; // 유저가 들어간 그룹간

    @PrePersist
    protected void prePersist() {
        if (this.profile == null) {
            this.profile = new Profile(this);
        }
    }

}
