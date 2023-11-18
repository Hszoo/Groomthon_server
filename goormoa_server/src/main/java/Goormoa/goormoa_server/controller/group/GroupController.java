package Goormoa.goormoa_server.controller.group;


import Goormoa.goormoa_server.controller.follow.FollowController;
import Goormoa.goormoa_server.dto.group.GroupDTO;
import Goormoa.goormoa_server.dto.group.DividedGroups;
import Goormoa.goormoa_server.dto.group.GroupDetailDTO;
import Goormoa.goormoa_server.service.auth.AuthenticationService;
import Goormoa.goormoa_server.service.group.GroupService;
import Goormoa.goormoa_server.entity.user.User;
import Goormoa.goormoa_server.repository.user.UserRepository;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/group")
public class GroupController {
    private final AuthenticationService authenticationService;
    private final GroupService groupService;
    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(GroupController.class);

    /* 모집 중인 모임 조회 */
    @GetMapping
    public List<GroupDTO> getAllRecruitingGroups() {
        logger.info("모임 컨트롤러 -> 모집 중인 모임 조회");
        String currentUserEmail = authenticationService.getCurrentAuthenticatedUserEmail();
        return groupService.getAllGroups(currentUserEmail);
    }

    /* 현재 사용자가 포함된 모든 모임 조회 */
    @GetMapping("/myGroups")
    public DividedGroups getMyGroupList() {
        logger.info("모임 컨트롤러 -> 현재 사용자 모든 모임 조회");
        String currentUserEmail = authenticationService.getCurrentAuthenticatedUserEmail();
        return groupService.getMyGroups(currentUserEmail);
    }
    /* 특정 사용자가 포함된 모든 모임 조회 */
    @GetMapping("/myGroups/{userId}")
    public DividedGroups getOtherUserGroupList(@PathVariable Long userId) {
        logger.info("모임 컨트롤러 -> 특정 사용자 모든 모임 조회");
        Optional<User> optionalUser = userRepository.findByUserId(userId);
        if(!optionalUser.isPresent()) {
            return null;
        }
        User user = optionalUser.get();
        return groupService.getMyGroups(user.getUserEmail());
    }

    // 생성 처리
    @PostMapping
    public ResponseEntity<String> groupCreate(@RequestBody GroupDTO groupDTO) {
        logger.info("모임 컨트롤러 -> 모임 생성");
        String currentUserEmail = authenticationService.getCurrentAuthenticatedUserEmail();
        String signupResult = groupService.save(currentUserEmail, groupDTO);
        return "error".equals(signupResult) ?
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body("그룹 생성 실패") :
                ResponseEntity.ok(signupResult);
    }

    // 수정 처리 : 모임의 host와 사용자가 같을 때만 가능
    @PostMapping("/update")
    public ResponseEntity<String> groupUpdate(@RequestBody GroupDTO groupDTO) {
        logger.info("모임 컨트롤러 -> 모임 수정 요청");
        String currentUserEmail = authenticationService.getCurrentAuthenticatedUserEmail();
        String updateResult = groupService.update(currentUserEmail, groupDTO);
        return "error".equals(updateResult) ?
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body("그룹 업데이트 실패") :
                ResponseEntity.ok(updateResult);
    }

    // 삭제 처리 : 모임의 host와 사용자가 같을 때만 가능
    @DeleteMapping("/delete")
    public ResponseEntity<String> groupDelete(@RequestBody GroupDTO groupDTO) {
        logger.info("그룹 컨트롤러 -> 모임 삭제 요청");
        String currentUserEmail = authenticationService.getCurrentAuthenticatedUserEmail();
        String deleteResult = groupService.delete(currentUserEmail, groupDTO);
        return "error".equals(deleteResult) ?
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body("그룹 삭제 실패") :
                ResponseEntity.ok(deleteResult);
    }

    /* 모임 상세 페이지 GET 요청 (완료)*/
    @GetMapping("/{groupId}")
    public GroupDTO getGroup(@PathVariable Long groupId) {
        logger.info("그룹 컨트롤러 -> 모임 상세 페이지 요청");
        return groupService.detailGroup(groupId);
    }
    /* 모임 모집 마감 요청 */
    @PostMapping("/complete")
    public void groupClose(@RequestBody GroupDTO groupDTO) {
        logger.info("그룹 컨트롤러 -> 모임 모집 마감 요청");
        String currentUserEmail = authenticationService.getCurrentAuthenticatedUserEmail();
        groupService.closeGroup(currentUserEmail, groupDTO);
    }

    /* 모임 신청 요청 */
    @PostMapping("/{groupId}/apply")
    public String applyToGroup(@PathVariable Long groupId) {
        logger.info("그룹 컨트롤러 -> 모임 신청 요청");
        String currentUserEmail = authenticationService.getCurrentAuthenticatedUserEmail();
        return groupService.applyToGroup(groupId, currentUserEmail);
    }
    /* 모임 신청 승인 */
    @PostMapping("/{groupId}/approve/{userId}")
    public String approveUserToGroup(@PathVariable Long groupId, @PathVariable Long userId) {
        logger.info("그룹 컨트롤러 -> 모임 신청 승인");
        return groupService.approveApplication(groupId, userId);
    }

    /* 모임 신청 거부 */
    @PostMapping("/{groupId}/reject/{userId}")
    public String rejectUserToGroup(@PathVariable Long groupId, @PathVariable Long userId) {
        logger.info("그룹 컨트롤러 -> 모임 신청 거부");
        return groupService.rejectApplication(groupId, userId);
    }

}
