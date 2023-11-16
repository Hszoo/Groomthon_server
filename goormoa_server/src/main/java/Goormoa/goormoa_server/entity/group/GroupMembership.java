package Goormoa.goormoa_server.entity.group;

import Goormoa.goormoa_server.entity.group.Group;
import Goormoa.goormoa_server.entity.profile.Profile;
import lombok.*;

import javax.persistence.*;
/* group 과 user 간의 다대다 매핑 관계 표현 테이블 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "group_membership")
public class GroupMembership {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "profile_uid")
    private Profile profile;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

}



