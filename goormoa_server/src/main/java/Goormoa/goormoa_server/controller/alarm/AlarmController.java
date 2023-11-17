package Goormoa.goormoa_server.controller.alarm;

import Goormoa.goormoa_server.controller.profile.ProfileController;
import Goormoa.goormoa_server.dto.alarm.AlarmDTO;
import Goormoa.goormoa_server.service.alarm.AlarmService;
import Goormoa.goormoa_server.service.auth.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AlarmController {
    private final AuthenticationService authenticationService;
    private final AlarmService alarmService;
    private static final Logger logger = LoggerFactory.getLogger(AlarmController.class);

    @GetMapping("/alarmList") // 알람 목록 가져오기
    public ResponseEntity<List<AlarmDTO>> getAlarm() {
        logger.info("알람 컨트롤러 -> 알람 목록 조회");
        String currentUserEmail = authenticationService.getCurrentAuthenticatedUserEmail();
        return ResponseEntity.ok(alarmService.getAlarms(currentUserEmail));
    }
}