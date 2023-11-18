//package Goormoa.goormoa_server.repository.chat;
//
//import Goormoa.goormoa_server.entity.chat.ChatRoom;
//import Goormoa.goormoa_server.entity.chat.Message;
//import org.springframework.data.jpa.repository.JpaRepository;
//
//import java.util.List;
//
//public interface MessageRepository extends JpaRepository<Message, Long> {
//    // ChatRoom 엔티티의 chatRoomId 필드를 사용하여 Message 엔티티를 조회
//    List<Message> findByChatRoom_ChatRoomId(Long chatRoomId);
//    List<Message> findByChatRoom(ChatRoom chatRoom);
//}