//package Goormoa.goormoa_server.entity.chat;
//
//
//import Goormoa.goormoa_server.entity.user.User;
//import lombok.*;
//
//import javax.persistence.*;
//import java.time.LocalDateTime;
//
//@Entity
//@Setter
//@Getter
//@Builder
//@AllArgsConstructor
//@NoArgsConstructor
//@Data
//@Table(name = "message_table")
//public class Message {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long messageId; // 메세지 고유 식별자
//
//    @ManyToOne
//    @JoinColumn(name = "chat_room")
//    private ChatRoom chatRoom; // 해당 메세지의 해당 그룹
//
//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private User user; // 보내는 사람
//
//    @Column(nullable = false)
//    private String content; // 메세지 내용
//
//    @Column(nullable = false)
//    private LocalDateTime timestamp;
//
//    @PrePersist
//    public void prePersist() {
//        if (timestamp == null) {
//            timestamp = LocalDateTime.now(); // 현재 시간으로 초기화
//        }
//    }
//}
