package Goormoa.goormoa_server.dto.alarm;

import Goormoa.goormoa_server.dto.group.GroupMemberDTO;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class GroupAlarmDTO {
    private Long groupId;
    private GroupMemberDTO groupMemberDTO;
}
