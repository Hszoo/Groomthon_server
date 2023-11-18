package Goormoa.goormoa_server.service.recommend;

import Goormoa.goormoa_server.dto.recommend.RecommendFriendDTO;
import Goormoa.goormoa_server.entity.follow.Follow;
import Goormoa.goormoa_server.entity.user.User;
import Goormoa.goormoa_server.repository.follow.FollowRepository;
import Goormoa.goormoa_server.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class FriendRecommendationService {
    private final UserRepository userRepository;
    private final FollowRepository followRepository;

    public List<RecommendFriendDTO> getRecommendFriendList(String currentUserEmail) {
        Optional<User> optionalUser = userRepository.findByUserEmail(currentUserEmail);
        if (!optionalUser.isPresent()) {
            return Collections.emptyList();
        }

        User currentUser = optionalUser.get();
        Set<Long> followedUserIds = new HashSet<>();


        List<Follow> fromFollows = followRepository.findByFromUserUserId(currentUser.getUserId());
        for (Follow follow : fromFollows) {
            followedUserIds.add(follow.getFromUser().getUserId());
        }

        List<Follow> toFollows = followRepository.findByToUserUserId(currentUser.getUserId());
        for (Follow follow : toFollows) {
            followedUserIds.add(follow.getToUser().getUserId()); // 수정된 부분
        }

        List<RecommendFriendDTO> recommendFriendList = new ArrayList<>();
        for (Long followedUserId : followedUserIds) {
            addRecommendFriends(followedUserId, followedUserIds, recommendFriendList, currentUser);
        }

        Collections.shuffle(recommendFriendList);
        return recommendFriendList;
    }

    private void addRecommendFriends(Long userId, Set<Long> followedUserIds, List<RecommendFriendDTO> recommendFriendList, User currentUser) {
        List<Follow> follows = followRepository.findByFromUserUserId(userId);
        for (Follow follow : follows) {
            User recommendUser = follow.getToUser();
            if (!recommendUser.getUserId().equals(currentUser.getUserId()) && !followedUserIds.contains(recommendUser.getUserId())) {
                RecommendFriendDTO dto = convertToDTO(recommendUser);
                if (!recommendFriendList.contains(dto)) {
                    recommendFriendList.add(dto);
                }
            }
        }

        List<Follow> followers = followRepository.findByToUserUserId(userId);
        for (Follow follower : followers) {
            User recommendUser = follower.getFromUser();
            if (!recommendUser.getUserId().equals(currentUser.getUserId()) && !followedUserIds.contains(recommendUser.getUserId())) {
                RecommendFriendDTO dto = convertToDTO(recommendUser);
                if (!recommendFriendList.contains(dto)) {
                    recommendFriendList.add(dto);
                }
            }
        }


    }

    private RecommendFriendDTO convertToDTO(User user) {
        RecommendFriendDTO dto = new RecommendFriendDTO();
        dto.setUserId(user.getUserId());
        dto.setUserName(user.getUserName());
        dto.setUserEmail(user.getUserEmail());
        dto.setProfileImg(user.getProfile().getProfileImg());
        return dto;
    }
}

