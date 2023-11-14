package Goormoa.goormoa_server.service.follow;

import Goormoa.goormoa_server.dto.follow.FollowDTO;
import Goormoa.goormoa_server.dto.profile.ProfileFollowListDTO;
import Goormoa.goormoa_server.entity.follow.Follow;
import Goormoa.goormoa_server.entity.user.User;
import Goormoa.goormoa_server.repository.follow.FollowRepository;
import Goormoa.goormoa_server.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FollowService {
    private final FollowRepository followRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public String toggleFollow(Long targetUserId, String currentUserEmail) {
        User currentUser = getUserByEmail(currentUserEmail);
        User targetUser = getUserById(targetUserId);

        if (currentUser.getUserId().equals(targetUserId)) {
            return "error";
        }

        return followRepository.findByToUserAndFromUser(targetUser, currentUser)
                .map(follow -> {
                    System.out.println(currentUser.getUserName() + "님이 " + targetUser.getUserName() + "님을 언팔로우 했습니다.");
                    followRepository.delete(follow);
                    return "success 1";
                }).orElseGet(() -> {
                    System.out.println(currentUser.getUserName() + "님이 " + targetUser.getUserName() + "님을 팔로우 했습니다.");
                    followRepository.save(new Follow(targetUser, currentUser));
                    return "success 2";
                });
    }

    public List<FollowDTO> getFollowers(String currentUserEmail) {
        User currentUser = getUserByEmail(currentUserEmail);
        return mapFollowsToFollowDTO(followRepository.findByFromUserUserId(currentUser.getUserId()));
    }

    public List<FollowDTO> getFollowing(String currentUserEmail) {
        User currentUser = getUserByEmail(currentUserEmail);
        return mapFollowsFromFollowDTO(followRepository.findByToUserUserId(currentUser.getUserId()));
    }

    private User getUserByEmail(String email) {
        return userRepository.findByUserEmail(email)
                .orElseThrow(() -> new NoSuchElementException("User not found with email: " + email));
    }

    private User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User not found with id: " + id));
    }

    private List<FollowDTO> mapFollowsToFollowDTO(List<Follow> follows) {
        return follows.stream()
                .map(follow -> {
                    FollowDTO followDTO = modelMapper.map(follow.getToUser(), FollowDTO.class);
                    ProfileFollowListDTO profileFollowListDTO = new ProfileFollowListDTO();
                    // ProfileFollowListDTO에 필요한 정보를 설정해야 합니다.
                    profileFollowListDTO.setProfileId(follow.getToUser().getProfile().getProfileId());
                    profileFollowListDTO.setProfileImg(follow.getToUser().getProfile().getProfileImg());
                    followDTO.setProfileFollowListDTO(profileFollowListDTO);
                    return followDTO;
                })
                .collect(Collectors.toList());
    }
    private List<FollowDTO> mapFollowsFromFollowDTO(List<Follow> follows) {
        return follows.stream()
                .map(follow -> {
                    FollowDTO followDTO = modelMapper.map(follow.getFromUser(), FollowDTO.class);
                    ProfileFollowListDTO profileFollowListDTO = new ProfileFollowListDTO();
                    // ProfileFollowListDTO에 필요한 정보를 설정해야 합니다.
                    profileFollowListDTO.setProfileId(follow.getFromUser().getProfile().getProfileId());
                    profileFollowListDTO.setProfileImg(follow.getFromUser().getProfile().getProfileImg());
                    followDTO.setProfileFollowListDTO(profileFollowListDTO);
                    return followDTO;
                })
                .collect(Collectors.toList());
    }
}
