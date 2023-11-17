package Goormoa.goormoa_server.dto.alarm;

import Goormoa.goormoa_server.entity.alarm.AlarmType;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AlarmDTO {
    private Long userId; // 알람 받는 유저 io
    private String content; // 알람 내용
    private AlarmType alarmType; // 알람 타입
    private FollowAlarmDTO followAlarmDTO;
    private GroupAlarmDTO groupAlarmDTO;
    private AcceptAlarmDTO acceptAlarmDTO;
    private FinishAlarmDTO finishAlarmDTO;
    private RejectAlarmDTO rejectAlarmDTO;

//    private FollowDTO followDTO; // 팔로우 건 유저 id
//    private Long groupUserId; // 그룹 신청한 유저 id
//    private Long groupId; // 그룹 id
}
