//package Goormoa.goormoa_server.entity.chat;
//
//import Goormoa.goormoa_server.entity.group.Group;
//import lombok.*;
//
//import javax.persistence.*;
//
//@Entity
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
//@Table(name="chat_room_table")
//public class ChatRoom {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long chatRoomId; // 채팅방 고유 식별자
//
//    @OneToOne
//    @JoinColumn(name = "group_id")
//    private Group group; // 해당 채팅방의 그룹
//
//    public ChatRoom(Group group) {
//        this.group = group;
//    }
//}
