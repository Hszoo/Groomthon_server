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

    @PostMapping
    public ResponseEntity<String> groupCreate(@RequestBody GroupDTO groupDTO) {
        String currentUserEmail = authenticationService.getCurrentAuthenticatedUserEmail();
        String signupResult = groupService.save(currentUserEmail ,groupDTO);
        return "error".equals(signupResult) ?
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body("그룹 생성 실패") :
                ResponseEntity.ok(signupResult);
    }

    @PostMapping("/update")
    public ResponseEntity<String> groupUpdate(@RequestBody GroupDTO groupDTO) {
        String currentUserEmail = authenticationService.getCurrentAuthenticatedUserEmail();
        String updateResult = groupService.update(currentUserEmail ,groupDTO);
        return "error".equals(updateResult) ?
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body("그룹 업데이트 실패") :
                ResponseEntity.ok(updateResult);
    }

    @PostMapping("/delete")
    public ResponseEntity<String> groupDelete(@RequestBody GroupDTO groupDTO) {
        String currentUserEmail = authenticationService.getCurrentAuthenticatedUserEmail();
        String deleteResult = groupService.delete(currentUserEmail ,groupDTO);
        return "error".equals(deleteResult) ?
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body("그룹 삭제 실패") :
                ResponseEntity.ok(deleteResult);
    }

}