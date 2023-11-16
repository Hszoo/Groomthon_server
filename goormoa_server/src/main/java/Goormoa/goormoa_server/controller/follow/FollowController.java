package Goormoa.goormoa_server.controller.follow;

import Goormoa.goormoa_server.dto.follow.FollowDTO;
import Goormoa.goormoa_server.dto.follow.FollowListDTO;
import Goormoa.goormoa_server.service.auth.AuthenticationService;
import Goormoa.goormoa_server.service.follow.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/follow")
public class FollowController {
    private final FollowService followService;
    private final AuthenticationService authenticationService;

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

