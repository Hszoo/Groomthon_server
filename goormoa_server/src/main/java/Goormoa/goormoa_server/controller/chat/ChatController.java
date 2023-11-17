//package Goormoa.goormoa_server.controller.chat;
//
//import Goormoa.goormoa_server.dto.chat.MessageDTO;
//import Goormoa.goormoa_server.repository.chat.MessageRepository;
//import Goormoa.goormoa_server.service.auth.AuthenticationService;
//import Goormoa.goormoa_server.service.chat.ChatRoomService;
//import Goormoa.goormoa_server.service.user.UserService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.messaging.handler.annotation.DestinationVariable;
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.handler.annotation.SendTo;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequiredArgsConstructor
//public class ChatController {
//    private final ChatRoomService chatRoomService; // 채팅방 서비스 주입
//    private final AuthenticationService authenticationService;
//    private final MessageRepository messageRepository;
//    private final UserService userService;
//
////    @GetMapping("/chat/{chatRoomId}")
////    public ResponseEntity<List<Message>> enterChatRoom(@PathVariable Long chatRoomId) {
////        List<Message> messages = messageRepository.findByChatRoom_ChatRoomId(chatRoomId);
////        return ResponseEntity.ok(messages); // JSON 형식으로 데이터 반환
////    }
//
//    @MessageMapping("/chat/{chatRoomId}")
//    @SendTo("/topic/messages/{chatRoomId}")
//    public MessageDTO sendMessage(@DestinationVariable Long chatRoomId, MessageDTO messageDTO) {
//        System.out.println("[메세지 보내기 시작]");
//        System.out.println(messageDTO);
//        System.out.println("chatRoomID"+chatRoomId);
////        String currentUserEmail = authenticationService.getCurrentAuthenticatedUserEmail();
//        String currentUserEmail = "wjdahrrla@naver.com";
//        Boolean isMemberOfChatRoomResult = chatRoomService.isMemberOfChatRoom(chatRoomId, currentUserEmail);
//        if(isMemberOfChatRoomResult) {
//            System.out.println("=====================true=====================");
//            // 현재 사용자 ID 설정
//            messageDTO.setUserId(userService.findUserEmail(currentUserEmail).get().getUserId());
//            // 메시지 저장
//            chatRoomService.saveMessage(chatRoomId, messageDTO);
//            System.out.println("=====================save=====================");
//            return messageDTO;
//        }
//        else {
//            System.out.println("=====================false=====================");
//            return null;
//        }
//    }
//}
