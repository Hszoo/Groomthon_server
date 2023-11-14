package Goormoa.goormoa_server.controller.follow;

import Goormoa.goormoa_server.dto.follow.FollowDTO;
import Goormoa.goormoa_server.service.auth.AuthenticationService;
import Goormoa.goormoa_server.service.follow.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/follow")
public class FollowController {
    private final FollowService followService;
    private final AuthenticationService authenticationService;

    @PostMapping("/{targetUserId}")
    public ResponseEntity<String> followUser(@PathVariable Long targetUserId) {
        String currentUserEmail = authenticationService.getCurrentAuthenticatedUserEmail();
        try {
            String followResult = followService.toggleFollow(targetUserId, currentUserEmail);
            switch (followResult) {
                case "success 1":
                    return ResponseEntity.ok("Unfollow 성공");
                case "success 2":
                    return ResponseEntity.ok("Follow 성공");
                default:
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("알 수 없는 오류가 발생했습니다.");
            }
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/followers")
    public ResponseEntity<List<FollowDTO>> getFollowers() {
        String currentUserEmail = authenticationService.getCurrentAuthenticatedUserEmail();
        List<FollowDTO> followers = followService.getFollowers(currentUserEmail);
        for(int i=0; i<followers.size(); i++) {
            System.out.println("[followers]");
            System.out.println(followers.get(i).getUserId());
        }
        return ResponseEntity.ok(followers);
    }

    @GetMapping("/following")
    public ResponseEntity<List<FollowDTO>> getFollowing() {
        String currentUserEmail = authenticationService.getCurrentAuthenticatedUserEmail();
        List<FollowDTO> following = followService.getFollowing(currentUserEmail);
        for(int i=0; i<following.size(); i++) {
            System.out.println("[followings]");
            System.out.println(following.get(i).getUserId());
        }
        return ResponseEntity.ok(following);
    }

}
