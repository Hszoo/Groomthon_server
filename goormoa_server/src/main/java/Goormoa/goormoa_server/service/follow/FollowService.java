package Goormoa.goormoa_server.service.follow;

import Goormoa.goormoa_server.dto.alarm.FollowAlarmDTO;
import Goormoa.goormoa_server.dto.follow.FollowDTO;
import Goormoa.goormoa_server.dto.follow.FollowDetailListDTO;
import Goormoa.goormoa_server.dto.follow.FollowListDTO;
import Goormoa.goormoa_server.dto.user.UserFollowAlarmDTO;
import Goormoa.goormoa_server.entity.alarm.FollowAlarm;
import Goormoa.goormoa_server.entity.follow.Follow;
import Goormoa.goormoa_server.entity.user.User;
import Goormoa.goormoa_server.repository.alarm.AlarmRepository;
import Goormoa.goormoa_server.repository.follow.FollowRepository;
import Goormoa.goormoa_server.repository.user.UserRepository;
import Goormoa.goormoa_server.service.alarm.AlarmService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FollowService {
    private final FollowRepository followRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final AlarmRepository alarmRepository;
    private final AlarmService alarmService;

    public String toggleFollowing(Long targetUserId, String currentUserEmail) {
        User currentUser = getUserByEmail(currentUserEmail);
        User targetUser = getUserById(targetUserId);

        if (currentUser.getUserId().equals(targetUserId)) {
            return "error";
        }
        Optional<Follow> followOptional = followRepository.findByToUserAndFromUser(targetUser, currentUser);
        if (followOptional.isPresent()) {
            Optional<FollowAlarm> followAlarmOptional = alarmRepository.findByFollowFollowId(followOptional.get().getFollowId());
            if(followAlarmOptional.isPresent()) {
                alarmRepository.delete(followAlarmOptional.get());
            }
            followRepository.delete(followOptional.get());
            return "UnFollow 성공";
        } else {
            // 팔로우하지 않은 경우 팔로우 처리 (왜 저장이 되는지 모르겠음,,,)
//            Follow follow = new Follow(targetUser, currentUser);
//            followRepository.save(follow);

            // 팔로우 알람 생성 및 저장
            FollowAlarmDTO followAlarmDTO = new FollowAlarmDTO();
            FollowDTO followDTO = new FollowDTO();
            followDTO.setToUser(convertToUserDTO(targetUser));
            followDTO.setFromUser(convertToUserDTO(currentUser));
            followAlarmDTO.setFollowDTO(followDTO);
            alarmService.saveFollowAlarm(currentUserEmail, followAlarmDTO);

            return "Follow 성공";
        }
    }

    public String deleteFollower(Long targetUserId, String currentUserEmail) {
        User currentUser = getUserByEmail(currentUserEmail);
        User targetUser = getUserById(targetUserId);

        if (!currentUser.getUserId().equals(targetUserId)) {
            Optional<Follow> optionalFollow = followRepository.findByToUserAndFromUser(currentUser, targetUser);
            if(optionalFollow.isPresent()) {
                followRepository.delete(optionalFollow.get());
                return "팔로워 삭제 성공";
            }
        }
        return "error";
    }

    private List<FollowListDTO> mapFollowsToFollowDTO(List<Follow> follows, boolean isToUser) {
        List<FollowListDTO> followListDTOS = new ArrayList<>();
        for (Follow follow : follows) {
            User user = isToUser ? follow.getToUser() : follow.getFromUser();
            FollowListDTO followListDTO = modelMapper.map(user, FollowListDTO.class);

            if(user.getProfile() != null) {
                FollowDetailListDTO followDetailListDTO = new FollowDetailListDTO(user.getProfile().getProfileId(), user.getProfile().getProfileImg());
                followListDTO.setFollowDetailListDTO(followDetailListDTO);
                followListDTOS.add(followListDTO);
            }

        }
        return followListDTOS;
    }
    public UserFollowAlarmDTO convertToUserDTO(User user) {
        return modelMapper.map(user, UserFollowAlarmDTO.class);
    }

    public List<FollowListDTO> getFollowers(String currentUserEmail) {
        User currentUser = getUserByEmail(currentUserEmail);
        List<Follow> follows = followRepository.findByToUserUserId(currentUser.getUserId());
        return mapFollowsToFollowDTO(follows, false);
    }

    public List<FollowListDTO> getFollowing(String currentUserEmail) {
        User currentUser = getUserByEmail(currentUserEmail);
        List<Follow> follows = followRepository.findByFromUserUserId(currentUser.getUserId());
        return mapFollowsToFollowDTO(follows, true);
    }
    private User getUserByEmail(String email) {
        return userRepository.findByUserEmail(email)
                .orElseThrow(() -> new NoSuchElementException("유저 이메일을 찾을 수 없습니다. " + email));
    }

    private User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("유저를 찾을 수 없습니다. " + id));
    }

}