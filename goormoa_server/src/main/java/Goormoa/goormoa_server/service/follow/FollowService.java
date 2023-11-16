package Goormoa.goormoa_server.service.follow;

import Goormoa.goormoa_server.dto.alarm.FollowAlarmDTO;
import Goormoa.goormoa_server.dto.follow.FollowDTO;
import Goormoa.goormoa_server.dto.follow.FollowListDTO;
import Goormoa.goormoa_server.entity.alarm.Alarm;
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
            Follow follow = new Follow(targetUser, currentUser);
            followRepository.save(follow);
            FollowAlarmDTO followAlarmDTO = new FollowAlarmDTO();
            followAlarmDTO.setFollow(follow);
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

    private List<FollowDTO> mapFollowsToFollowDTO(List<Follow> follows, boolean isToUser) {
        List<FollowDTO> followDTOs = new ArrayList<>();
        for (Follow follow : follows) {
            User user = isToUser ? follow.getToUser() : follow.getFromUser();
            FollowDTO followDTO = modelMapper.map(user, FollowDTO.class);

            FollowListDTO followListDTO = new FollowListDTO();
            followListDTO.setProfileId(user.getProfile().getProfileId());
            followListDTO.setProfileImg(user.getProfile().getProfileImg());

            followDTO.setFollowListDTO(followListDTO);
            followDTOs.add(followDTO);
        }
        return followDTOs;
    }

    public List<FollowDTO> getFollowers(String currentUserEmail) {
        User currentUser = getUserByEmail(currentUserEmail);
        List<Follow> follows = followRepository.findByToUserUserId(currentUser.getUserId());
        return mapFollowsToFollowDTO(follows, false);
    }

    public List<FollowDTO> getFollowing(String currentUserEmail) {
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
