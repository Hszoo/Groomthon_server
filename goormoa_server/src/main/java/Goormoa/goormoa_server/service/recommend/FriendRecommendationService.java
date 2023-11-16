//package Goormoa.goormoa_server.service.recommend;
//
//import Goormoa.goormoa_server.dto.recommend.RecommendFriendDTO;
//import Goormoa.goormoa_server.entity.user.User;
//import Goormoa.goormoa_server.repository.follow.FollowRepository;
//import Goormoa.goormoa_server.repository.user.UserRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//@RequiredArgsConstructor
//public class FriendRecommendationService {
//    private final UserRepository userRepository;
//    private final FollowRepository followRepository;
//    public List<RecommendFriendDTO> getRecommendFriendList(String currentUserEmail) {
//        Optional<User> optionalUser = findUserEmail(currentUserEmail);
//        if(optionalUser.isPresent()) {
//            List<User> myFollows = followRepository.findByToUserUserId(optionalUser.get().getUserId());
//
//        }
//
//        return null;
//    }
//
//    public Optional<User> findUserEmail(String currentUserEmail) {
//        return userRepository.findByUserEmail(currentUserEmail);
//    }
//}
