package Goormoa.goormoa_server.controller.group;


import Goormoa.goormoa_server.dto.group.DividedGroups;
import Goormoa.goormoa_server.dto.group.GroupDTO;
import Goormoa.goormoa_server.repository.group.GroupRepository;
import Goormoa.goormoa_server.repository.user.UserRepository;
import Goormoa.goormoa_server.service.auth.AuthenticationService;
import Goormoa.goormoa_server.service.group.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/group")
public class GroupController {
    private final GroupService groupService;
    private final AuthenticationService authenticationService;
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;

    /* 모집 중인 모임 조회 */
    @GetMapping
    public List<GroupDTO> getRecruitingGroups() {
        return groupService.getRecruitingGroups();
    }

    /* 모임 검색 : 전체 모임 데이터 GET 요청 */
    @GetMapping("/all")
    public List<GroupDTO> getGroupList() {
        return groupService.getAllGroups();
    }

    /* 사용자가 포함된 모든 모임 조회 */
    @GetMapping("/myGroups")
    public DividedGroups getMyGroupList() {
        String currentUserEmail = authenticationService.getCurrentAuthenticatedUserEmail();
        return groupService.getMyGroups(currentUserEmail);
    }

    @PostMapping
    public ResponseEntity<String> groupCreate(@RequestBody GroupDTO groupDTO) {
        String currentUserEmail = authenticationService.getCurrentAuthenticatedUserEmail();
        String signupResult = groupService.save(currentUserEmail, groupDTO);
        return "error".equals(signupResult) ?
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body("그룹 생성 실패") :
                ResponseEntity.ok(signupResult);
    }

    // 수정 처리 : 모임의 host와 사용자가 같을 때만 가능
    @PostMapping("/update")
    public ResponseEntity<String> groupUpdate(@RequestBody GroupDTO groupDTO) {
        String currentUserEmail = authenticationService.getCurrentAuthenticatedUserEmail();
        String updateResult = groupService.update(currentUserEmail, groupDTO);
        return "error".equals(updateResult) ?
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body("그룹 업데이트 실패") :
                ResponseEntity.ok(updateResult);
    }

    // 삭제 처리 : 모임의 host와 사용자가 같을 때만 가능
    @DeleteMapping("/delete")
    public ResponseEntity<String> groupDelete(@RequestBody GroupDTO groupDTO) {
        String currentUserEmail = authenticationService.getCurrentAuthenticatedUserEmail();
        String deleteResult = groupService.delete(currentUserEmail, groupDTO);
        return "error".equals(deleteResult) ?
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body("그룹 삭제 실패") :
                ResponseEntity.ok(deleteResult);
    }

    /* 모임 상세 페이지 GET 요청 (완료)*/
    @GetMapping("/{groupId}")
    public GroupDTO getGroup(@PathVariable Long groupId) {
        String currentUserEmail = authenticationService.getCurrentAuthenticatedUserEmail();
        return groupService.detailGroup(currentUserEmail, groupId);
    }


    /* 모임 모집 마감 요청 */
    @PostMapping("/complete")
    public void groupClose(@RequestBody GroupDTO groupDTO) {
        String currentUserEmail = authenticationService.getCurrentAuthenticatedUserEmail();
        groupService.closeGroup(currentUserEmail, groupDTO);
    }

    /* 모임 신청 요청 */
    @PostMapping("/{groupId}/apply")
    public String applyToGroup(@PathVariable Long groupId) {
        String currentUserEmail = authenticationService.getCurrentAuthenticatedUserEmail();
        return groupService.applyToGroup(groupId, currentUserEmail);
    }

}
