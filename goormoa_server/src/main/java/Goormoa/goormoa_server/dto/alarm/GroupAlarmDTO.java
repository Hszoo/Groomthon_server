package Goormoa.goormoa_server.dto.alarm;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class GroupAlarmDTO {
    private Long groupUserId;
    private Long groupId;
}
