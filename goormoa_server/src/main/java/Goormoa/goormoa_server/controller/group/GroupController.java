package Goormoa.goormoa_server.controller.group;


import Goormoa.goormoa_server.dto.group.DividedGroups;
import Goormoa.goormoa_server.dto.group.GroupDTO;
import Goormoa.goormoa_server.service.auth.AuthenticationService;
import Goormoa.goormoa_server.service.group.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/group")
public class GroupController {
    private final GroupService groupService;
    private final AuthenticationService authenticationService;

    @PostMapping // 그룹생성
    public ResponseEntity<String> groupCreate(@RequestBody GroupDTO groupDTO) {
        String currentUserEmail = authenticationService.getCurrentAuthenticatedUserEmail();
        return ResponseEntity.ok(groupService.save(currentUserEmail ,groupDTO));
    }

    @PostMapping("/update") // 그룹 업데이트
    public ResponseEntity<String> groupUpdate(@RequestBody GroupDTO groupDTO) {
        String currentUserEmail = authenticationService.getCurrentAuthenticatedUserEmail();
        return ResponseEntity.ok(groupService.update(currentUserEmail ,groupDTO));
    }

    @PostMapping("/delete") // 그룹 삭제
    public ResponseEntity<String> groupDelete(@RequestBody GroupDTO groupDTO) {
        String currentUserEmail = authenticationService.getCurrentAuthenticatedUserEmail();
        return ResponseEntity.ok(groupService.delete(currentUserEmail ,groupDTO));
    }

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