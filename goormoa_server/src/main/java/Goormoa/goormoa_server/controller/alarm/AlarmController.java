package Goormoa.goormoa_server.controller.alarm;

import Goormoa.goormoa_server.dto.alarm.AlarmDTO;
import Goormoa.goormoa_server.service.alarm.AlarmService;
import Goormoa.goormoa_server.service.auth.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AlarmController {
    private final AuthenticationService authenticationService;
    private final AlarmService alarmService;

//    @PostMapping("/hello")
//    public ResponseEntity<String> saveAlarm(@RequestBody AlarmDTO alarmDTO) {
//        String currentUserEmail = authenticationService.getCurrentAuthenticatedUserEmail();
//        String alarmResult = alarmService.save(currentUserEmail, alarmDTO);
//        if(Objects.equals(alarmResult, "success")) {
//            return ResponseEntity.ok("알람 저장 완료");
//        }
//        return ResponseEntity.ok("알람 저장 실패");
//    }

    @GetMapping("/alarmList") // 알람 목록 가져오기
    public ResponseEntity<List<AlarmDTO>> getAlarm() {
        String currentUserEmail = authenticationService.getCurrentAuthenticatedUserEmail();
        return ResponseEntity.ok(alarmService.getAlarms(currentUserEmail));
    }
    // 소캣 알람 구현 부분
//    private final SimpMessageSendingOperations messagingTemplate;
//
//    // stomp 테스트 화면
//    @GetMapping("/alarm/stomp")
//    public String stompAlarm() {
//        return "/stomp";
//    }
//
//    @MessageMapping("/{userId}")
//    public void message(@DestinationVariable("userId") Long userId) {
//        messagingTemplate.convertAndSend("/topic/" + userId, "alarm socket connection completed.");
//    }
}