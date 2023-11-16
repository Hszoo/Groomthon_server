package Goormoa.goormoa_server.entity.follow;

import Goormoa.goormoa_server.entity.user.User;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "follow_table")
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long followId;

    @ManyToOne
    @JoinColumn(name = "to_user")
    private User toUser;

    @ManyToOne
    @JoinColumn(name = "from_user")
    private User fromUser;

    public Follow(User toUser, User fromUser){
        this.toUser = toUser;
        this.fromUser = fromUser;
    }
}
