package Goormoa.goormoa_server.service.recommend;

import Goormoa.goormoa_server.dto.recommend.RecommendFriendDTO;
import Goormoa.goormoa_server.entity.follow.Follow;
import Goormoa.goormoa_server.entity.user.User;
import Goormoa.goormoa_server.repository.follow.FollowRepository;
import Goormoa.goormoa_server.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FriendRecommendationService {
    private final UserRepository userRepository;
    private final FollowRepository followRepository;
    public List<RecommendFriendDTO> getRecommendFriendList(String currentUserEmail) {
        Optional<User> optionalUser = findUserEmail(currentUserEmail);
        List<Follow> myFollowList = followRepository.findByToUserUserId(optionalUser.get().getUserId());
        List<RecommendFriendDTO> recommendFriendList = new ArrayList<>();
        if(optionalUser.isPresent()) {
            for(int i=0; i< myFollowList.size(); i++) {
                List<Follow> myFollowFollowList = followRepository.findByToUserUserId(myFollowList.get(i).getToUser().getUserId());
                for(int j=0; j<myFollowFollowList.size(); j++) {
                    User user = followRepository.findByToUser(myFollowFollowList.get(j).getToUser().getUserId());
                    RecommendFriendDTO recommendFriendDTO = new RecommendFriendDTO();
                    recommendFriendDTO.setUserId(user.getUserId());
                    recommendFriendDTO.setUserName(user.getUserName());
                    recommendFriendDTO.setUserEmail(user.getUserEmail());
                    recommendFriendList.add(recommendFriendDTO);
                }
            }
        }

        Collections.shuffle(recommendFriendList); // 리스트 섞기
        return recommendFriendList;
    }


    public Optional<User> findUserEmail(String currentUserEmail) {
        return userRepository.findByUserEmail(currentUserEmail);
    }
}
