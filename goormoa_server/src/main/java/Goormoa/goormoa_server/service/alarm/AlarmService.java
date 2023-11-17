package Goormoa.goormoa_server.service.alarm;

import Goormoa.goormoa_server.dto.alarm.AlarmDTO;
import Goormoa.goormoa_server.dto.alarm.FollowAlarmDTO;
import Goormoa.goormoa_server.dto.follow.FollowDTO;
import Goormoa.goormoa_server.dto.group.GroupDTO;
import Goormoa.goormoa_server.dto.user.UserFollowAlarmDTO;
import Goormoa.goormoa_server.entity.alarm.*;
import Goormoa.goormoa_server.entity.follow.Follow;
import Goormoa.goormoa_server.entity.group.Group;
import Goormoa.goormoa_server.entity.user.User;
import Goormoa.goormoa_server.repository.alarm.AlarmRepository;
import Goormoa.goormoa_server.repository.group.GroupRepository;
import Goormoa.goormoa_server.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AlarmService {
    private final UserRepository userRepository;
    private final AlarmRepository alarmRepository;
    private final GroupRepository groupRepository;
    private final ModelMapper modelMapper;
//    @PersistenceContext
//    private EntityManager entityManager;

    /* 팔로우 알림 전송 메소드 */
    @Transactional
    public void saveFollowAlarm(String currentUserEmail, FollowAlarmDTO followAlarmDTO) {
        User followToUser = convertToEntity(followAlarmDTO.getFollowDTO().getToUser());
        User followFromUser = getUser(currentUserEmail);
        AlarmType alarmType = AlarmType.FOLLOW;
        Follow follow = convertToEntity(followAlarmDTO.getFollowDTO());
//
//        Follow follow = convertToEntity(followAlarmDTO.getFollowDTO()); // 보류

        FollowAlarm followAlarm = new FollowAlarm();
        followAlarm.setUser(followToUser);
        followAlarm.setContent(followFromUser.getUserName() + "님이 회원님을 팔로우하기 시작했습니다.");
        followAlarm.setType(alarmType);

//        Follow follow = entityManager.merge(new Follow()); // 보류
        followAlarm.setFollow(follow);

        alarmRepository.save(followAlarm);
    }
    /* 그룹 신ㅇ 전송 메소드 */
    public void saveGroupAlarm(User host, User applicant, Group group) {
        String groupName = group.getGroupTitle();

        AlarmType alarmType = AlarmType.GROUP;
        GroupAlarm groupAlarm = new GroupAlarm();
        groupAlarm.setGroup(group);
        groupAlarm.setType(alarmType);
        groupAlarm.setUser(host);
        groupAlarm.setContent(applicant.getUserName()+"님이 '"+groupName+"' 구름에 참가 요청을 했습니다.");
        alarmRepository.save(groupAlarm); // applicant 의 userId 값이 같이 전송되는지 궁금
    }

    /* 그룹 마감 알림 전송 메소드 */
    public void saveFinishAlarm(String participantEmail, GroupDTO groupDTO) {
        Long userId = getUser(participantEmail).getUserId();
        String groupName = groupRepository.findById(groupDTO.getGroupId()).get().getGroupTitle();
        Group group = groupRepository.findByGroupId(groupDTO.getGroupId());

        AlarmType alarmType = AlarmType.FINISH;
        FinishAlarm finishAlarm = new FinishAlarm();
        finishAlarm.setGroup(group);
        finishAlarm.setType(alarmType);
        finishAlarm.setUser(userRepository.findById(userId).get());
        finishAlarm.setContent("‘"+groupName+"!’ 구름이 모집 마감되었습니다. 모임원들을 확인해 보세요!");

        alarmRepository.save(finishAlarm);
    }
    public void saveAcceptAlarm(Group group, User applicant) {
        String groupName = group.getGroupTitle();
        AcceptAlarm acceptAlarm = new AcceptAlarm();
        AlarmType alarmType = AlarmType.ACCEPT;
        acceptAlarm.setGroup(group);
        acceptAlarm.setUser(applicant);
        acceptAlarm.setType(alarmType);
        acceptAlarm.setContent("‘"+groupName+"구름 참여 승인되었습니다. 모임원들과 인사를 나눠보세요!👋");
        alarmRepository.save(acceptAlarm);
    }
    public void saveRejectAlarm(Group group, User applicant) {
        String groupName = group.getGroupTitle();
        RejectAlarm rejectAlarm = new RejectAlarm();
        AlarmType alarmType = AlarmType.REJECT;
        rejectAlarm.setGroup(group);
        rejectAlarm.setUser(applicant);
        rejectAlarm.setType(alarmType);
        rejectAlarm.setContent("‘"+groupName+"구름 참여 거절되었습니다. 다음 기회에 또 봬요!🥹");
        alarmRepository.save(rejectAlarm);
    }
    public List<AlarmDTO> getAlarms(String currentUserEmail) {
        User currentUser = getUser(currentUserEmail);
        List<Alarm> alarms = alarmRepository.findByUser(currentUser);

        List<AlarmDTO> alarmDTOs = new ArrayList<>();

        for (Alarm alarm : alarms) {
            AlarmDTO alarmDTO = new AlarmDTO();
            alarmDTO.setUserId(currentUser.getUserId());
            alarmDTO.setContent(alarm.getContent());
            alarmDTO.setAlarmType(alarm.getType());

            if (alarmDTO.getAlarmType().equals(AlarmType.FOLLOW)) {
                FollowAlarmDTO followAlarmDTO = new FollowAlarmDTO();
                FollowAlarm followAlarm = (FollowAlarm) alarm;

                UserFollowAlarmDTO toUserDTO = new UserFollowAlarmDTO(followAlarm.getFollow().getToUser().getUserId(),followAlarm.getFollow().getToUser().getUserName());
                UserFollowAlarmDTO fromUserDTO = new UserFollowAlarmDTO(followAlarm.getFollow().getFromUser().getUserId(), followAlarm.getFollow().getFromUser().getUserName());
                FollowDTO followDTO = new FollowDTO();
                followDTO.setFollowId(followAlarm.getFollow().getFollowId()); // 수정함
                followDTO.setToUser(toUserDTO);
                followDTO.setFromUser(fromUserDTO);

                followAlarmDTO.setFollowDTO(followDTO);
                alarmDTO.setFollowAlarmDTO(followAlarmDTO);
            }
            alarmDTOs.add(alarmDTO);
        }
        return alarmDTOs;
    }

    public User convertToEntity(UserFollowAlarmDTO userFollowAlarmDTO) { return modelMapper.map(userFollowAlarmDTO, User.class); }
    public Follow convertToEntity(FollowDTO followDTO) {
        return modelMapper.map(followDTO, Follow.class);
    }
    private User getUser(String email) {
        return userRepository.findByUserEmail(email).get();
    }
}

