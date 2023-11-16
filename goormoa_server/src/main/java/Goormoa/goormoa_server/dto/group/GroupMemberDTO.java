package Goormoa.goormoa_server.dto.group;

import Goormoa.goormoa_server.dto.user.ChatUserDTO;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GroupMemberDTO {
    private Long groupMemberId;
    private ChatUserDTO user;
}
