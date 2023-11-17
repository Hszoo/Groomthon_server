package Goormoa.goormoa_server.entity.group;

import Goormoa.goormoa_server.entity.user.User;
import lombok.*;

import javax.persistence.*;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="groupMember_table")
public class GroupMember { // 유저 별로 들어간 그룹을 저장해 줄 table
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long groupMemberId; // 멤버 고유 식별자

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user; // 사용자 식별자

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group; // 그룹 식별자

    public GroupMember(User user, Group group){
        this.user = user;
        this.group = group;
    }
}