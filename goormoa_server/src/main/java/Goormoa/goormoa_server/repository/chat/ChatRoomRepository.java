//package Goormoa.goormoa_server.repository.chat;
//
//import Goormoa.goormoa_server.entity.chat.ChatRoom;
//import Goormoa.goormoa_server.entity.user.User;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//
//import java.util.List;
//
//public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
//    // 사용자가 속한 그룹의 채팅방을 조회하는 메소드
//    @Query("SELECT cr FROM ChatRoom cr WHERE cr.group IN (SELECT gm.group FROM GroupMember gm WHERE gm.user = :user)")
//    List<ChatRoom> findByUser(User user);
//}
