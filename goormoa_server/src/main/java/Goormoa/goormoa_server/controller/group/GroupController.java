package Goormoa.goormoa_server.controller.group;


import Goormoa.goormoa_server.dto.group.GroupDTO;
import Goormoa.goormoa_server.service.auth.AuthenticationService;
import Goormoa.goormoa_server.service.group.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}