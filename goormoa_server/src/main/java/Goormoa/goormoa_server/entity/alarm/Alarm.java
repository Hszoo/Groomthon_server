package Goormoa.goormoa_server.entity.alarm;


import Goormoa.goormoa_server.entity.user.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "alarm_table")
public class Alarm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long alarmId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String content; // 알람 내용

    @Column(nullable = false)
    @Enumerated(EnumType.STRING) // Enum 값을 문자열로 저장
    private AlarmType type; // 알람의 타입

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt; // 알람 생성 시간

    @PrePersist
    protected void prePersist() {
        // createdAt 필드를 현재 시간으로 설정
        createdAt = LocalDateTime.now();
    }

}
