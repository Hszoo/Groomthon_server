package Goormoa.goormoa_server.controller.follow;

import Goormoa.goormoa_server.controller.alarm.AlarmController;
import Goormoa.goormoa_server.entity.user.User;
import Goormoa.goormoa_server.repository.user.UserRepository;
import Goormoa.goormoa_server.service.auth.AuthenticationService;
import Goormoa.goormoa_server.dto.follow.FollowListDTO;
import Goormoa.goormoa_server.service.follow.FollowService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/follow")
public class FollowController {
    private final AuthenticationService authenticationService;
    private final FollowService followService;
    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(FollowController.class);

    /* 다른 사용자의 팔로잉 조회 */
    @GetMapping("following/get/{userId}")
    public ResponseEntity<List<FollowListDTO>> getFollowers(@PathVariable Long userId) {
        logger.info("팔로우 컨트롤러 -> 다른 사용자의 팔로잉 조회");
        Optional<User> optionalUser = userRepository.findByUserId(userId);
        if (optionalUser.isEmpty()) {
            return null;
        }
        User user = optionalUser.get();
        List<FollowListDTO> followings = followService.getFollowing(user.getUserEmail());
        return ResponseEntity.ok(followings);
    }

    /* 다른 사용자의 팔로워 조회 */
    @GetMapping("followers/get/{userId}")
    public ResponseEntity<List<FollowListDTO>> followersOtherUser(@PathVariable Long userId) {
        logger.info("팔로우 컨트롤러 -> 다른 사용자의 팔로워 조회");
        Optional<User> optionalUser = userRepository.findByUserId(userId);
        if (optionalUser.isEmpty()) {
            return null;
        }
        User user = optionalUser.get();
        List<FollowListDTO> followers = followService.getFollowers(user.getUserEmail());
        return ResponseEntity.ok(followers);
    }

    @PostMapping("following/{targetUserId}") // 팔로잉 목록에서 팔로우 관리
    public ResponseEntity<String> followingUser(@PathVariable Long targetUserId) {
        logger.info("팔로우 컨트롤러 -> 팔로잉 목록에서 팔로우 관리");
        String currentUserEmail = authenticationService.getCurrentAuthenticatedUserEmail();
        return ResponseEntity.ok(followService.toggleFollowing(targetUserId, currentUserEmail));
    }

    @PostMapping("follower/{targetUserId}") // 팔로워 목록에서 팔로우 관리
    public ResponseEntity<String> followerUser(@PathVariable Long targetUserId) {
        logger.info("팔로우 컨트롤러 -> 팔로워 목록에서 팔로우 관리");
        String currentUserEmail = authenticationService.getCurrentAuthenticatedUserEmail();
        return ResponseEntity.ok(followService.deleteFollower(targetUserId, currentUserEmail));
    }

    @GetMapping("/followers") // 팔로워 목록 출력
    public ResponseEntity<List<FollowListDTO>> getFollowers() {
        logger.info("팔로우 컨트롤러 -> 팔로워 목록 조회");
        String currentUserEmail = authenticationService.getCurrentAuthenticatedUserEmail();
        List<FollowListDTO> followers = followService.getFollowers(currentUserEmail);
        return ResponseEntity.ok(followers);
    }

    @GetMapping("/following") // 팔로잉 목록 출력
    public ResponseEntity<List<FollowListDTO>> getFollowing() {
        logger.info("팔로우 컨트롤러 -> 팔로잉 목록 조회");
        String currentUserEmail = authenticationService.getCurrentAuthenticatedUserEmail();
        List<FollowListDTO> following = followService.getFollowing(currentUserEmail);
        return ResponseEntity.ok(following);
    }
}

