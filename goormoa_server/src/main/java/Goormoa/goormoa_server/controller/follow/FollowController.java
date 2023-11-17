package Goormoa.goormoa_server.controller.follow;

import Goormoa.goormoa_server.entity.user.User;
import Goormoa.goormoa_server.repository.user.UserRepository;
import Goormoa.goormoa_server.service.auth.AuthenticationService;
import Goormoa.goormoa_server.dto.follow.FollowListDTO;
import Goormoa.goormoa_server.service.follow.FollowService;
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
    /* 다른 사용자의 팔로잉 조회 */
    @GetMapping("following/get/{userId}")
    public ResponseEntity<List<FollowListDTO>> getFollowers(@PathVariable Long userId) {
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
        String currentUserEmail = authenticationService.getCurrentAuthenticatedUserEmail();
        return ResponseEntity.ok(followService.toggleFollowing(targetUserId, currentUserEmail));
    }

    @PostMapping("follower/{targetUserId}") // 팔로워 목록에서 팔로우 관리
    public ResponseEntity<String> followerUser(@PathVariable Long targetUserId) {
        String currentUserEmail = authenticationService.getCurrentAuthenticatedUserEmail();
        return ResponseEntity.ok(followService.deleteFollower(targetUserId, currentUserEmail));
    }

    @GetMapping("/followers") // 팔로워 목록 출력
    public ResponseEntity<List<FollowListDTO>> getFollowers() {
        String currentUserEmail = authenticationService.getCurrentAuthenticatedUserEmail();
        List<FollowListDTO> followers = followService.getFollowers(currentUserEmail);
        return ResponseEntity.ok(followers);
    }

    @GetMapping("/following") // 팔로잉 목록 출력
    public ResponseEntity<List<FollowListDTO>> getFollowing() {
        String currentUserEmail = authenticationService.getCurrentAuthenticatedUserEmail();
        List<FollowListDTO> following = followService.getFollowing(currentUserEmail);
        return ResponseEntity.ok(following);
    }
}

