package Goormoa.goormoa_server.service.recommend;

import Goormoa.goormoa_server.dto.recommend.RecommendFriendDTO;
import Goormoa.goormoa_server.entity.user.User;
import Goormoa.goormoa_server.repository.follow.FollowRepository;
import Goormoa.goormoa_server.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FriendRecommendationService {
    private final UserRepository userRepository;
    private final FollowRepository followRepository;

    public List<RecommendFriendDTO> getRecommendFriendList(String currentUserEmail) {
        return userRepository.findByUserEmail(currentUserEmail)
                .map(user -> getRecommendFriendsForUser(user))
                .orElse(Collections.emptyList());
    }

    private List<RecommendFriendDTO> getRecommendFriendsForUser(User user) {
        return followRepository.findByToUserUserId(user.getUserId()).stream()
                .flatMap(follow -> followRepository.findByToUserUserId(follow.getToUser().getUserId()).stream())
                .map(follow -> createRecommendFriendDTO(follow.getToUser()))
                .distinct()
                .collect(Collectors.collectingAndThen(Collectors.toList(), list -> {
                    Collections.shuffle(list);
                    return list;
                }));
    }

    private RecommendFriendDTO createRecommendFriendDTO(User user) {
        RecommendFriendDTO dto = new RecommendFriendDTO();
        dto.setUserId(user.getUserId());
        dto.setUserName(user.getUserName());
        dto.setUserEmail(user.getUserEmail());
        dto.setProfileImg(user.getProfile().getProfileImg());
        return dto;
    }
}

