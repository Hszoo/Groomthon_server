package Goormoa.goormoa_server.service.alarm;


import Goormoa.goormoa_server.dto.alarm.AlarmDTO;
import Goormoa.goormoa_server.dto.alarm.FollowAlarmDTO;
import Goormoa.goormoa_server.dto.follow.FollowDTO;
import Goormoa.goormoa_server.dto.user.UserFollowAlarmDTO;
import Goormoa.goormoa_server.entity.alarm.Alarm;
import Goormoa.goormoa_server.entity.alarm.AlarmType;
import Goormoa.goormoa_server.entity.alarm.FollowAlarm;
import Goormoa.goormoa_server.entity.follow.Follow;
import Goormoa.goormoa_server.entity.user.User;
import Goormoa.goormoa_server.repository.alarm.AlarmRepository;
import Goormoa.goormoa_server.repository.group.GroupRepository;
import Goormoa.goormoa_server.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void saveFollowAlarm(String currentUserEmail, FollowAlarmDTO followAlarmDTO) {
        User followToUser = getUser(currentUserEmail);
        User followFromUser = convertToEntity(followAlarmDTO.getFollowDTO().getFromUser());
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
//    public void saveGroupAlarm(String currentUserEmail, GroupAlarmDTO groupAlarmDTO) {
//        Long userId = getUser(currentUserEmail).getUserId();
//        String groupUserName = userRepository.findById(groupAlarmDTO.getGroupUserId()).get().getUserName();
//        String groupName = groupRepository.findById(groupAlarmDTO.getGroupId()).get().getGroupTitle();
//        AlarmType alarmType = AlarmType.FOLLOW;
//        GroupAlarm groupAlarm = new GroupAlarm();
//        groupAlarm.setUser(userRepository.findById(userId).get());
//        groupAlarm.setContent(groupUserName+"님이 '"+groupName+"' 구름에 참가 요청을 했습니다.");
//        groupAlarm.setType(alarmType);
//        groupAlarm.setGroupId(groupAlarmDTO.getGroupId());
//        groupAlarm.setGroupUserId(groupAlarm.getGroupUserId());
//        alarmRepository.save(groupAlarm);
//    }
//    public void saveAgreeAlarm(String currentUserEmail, AgreeAlarmDTO agreeAlarmDTO) {
//        Long userId = getUser(currentUserEmail).getUserId();
//        String groupName = groupRepository.findById(agreeAlarmDTO.getGroupId()).get().getGroupTitle();
//        AlarmType alarmType = AlarmType.FOLLOW;
//        AgreeAlarm agreeAlarm = new AgreeAlarm();
//        agreeAlarm.setUser(userRepository.findById(userId).get());
//        agreeAlarm.setContent("‘"+groupName+"!’ 구름이 모집 마감되었습니다. 모임원들을 확인해 보세요!");
//        agreeAlarm.setType(alarmType);
//        agreeAlarm.setGroupId(agreeAlarmDTO.getGroupId());
//        alarmRepository.save(agreeAlarm);
//    }
//    public void saveFinishAlarm(String currentUserEmail, FinishAlarmDTO finishAlarmDTO) {
//        Long userId = getUser(currentUserEmail).getUserId();
//        String groupName = groupRepository.findById(finishAlarmDTO.getGroupId()).get().getGroupTitle();
//        AlarmType alarmType = AlarmType.FOLLOW;
//        FinishAlarm finishAlarm = new FinishAlarm();
//        finishAlarm.setUser(userRepository.findById(userId).get());
//        finishAlarm.setContent("‘"+groupName+"구름 참여 승인되었습니다. 모임원들과 인사를 나눠보세요!👋");
//        finishAlarm.setType(alarmType);
//        finishAlarm.setGroupId(finishAlarm.getGroupId());
//        alarmRepository.save(finishAlarm);
//    }

    // 기타 알람이 있을 시 사용할 예정
//    public void saveEtcAlarm(String currentUserEmail, EtcAlarmDTO etcAlarmDTO) {
//        Long userId = getUser(currentUserEmail).getUserId();
//        AlarmType alarmType = AlarmType.FOLLOW;
//        AlarmDTO alarmDTO = new AlarmDTO();
//        alarmDTO.setUserId(userId);
//        alarmDTO.setAlarmType(alarmType);
//        alarmDTO.setEtcAlarmDTO(etcAlarmDTO);
//        alarmRepository.save(convertToEntity(alarmDTO));
//    }
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
                followDTO.setFollowId(followDTO.getFollowId());
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

