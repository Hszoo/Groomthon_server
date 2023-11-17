//package Goormoa.goormoa_server.controller.chat;
//
//import Goormoa.goormoa_server.dto.chat.ChatRoomDTO;
//import Goormoa.goormoa_server.dto.chat.ChatRoomDetailsDTO;
//import Goormoa.goormoa_server.dto.chat.MessageDTO;
//import Goormoa.goormoa_server.service.auth.AuthenticationService;
//import Goormoa.goormoa_server.service.chat.ChatRoomService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/chat")
//public class ChatRoomController {
//    final private ChatRoomService chatRoomService;
//    final private AuthenticationService authenticationService;
//
//    @GetMapping("/chatRoomList")
//    public ResponseEntity<List<ChatRoomDTO>> getChatRoom() {
//        String currentUserEmail = authenticationService.getCurrentAuthenticatedUserEmail();
//        List<ChatRoomDTO> chatRooms = chatRoomService.getChatRooms(currentUserEmail);
//        return ResponseEntity.ok(chatRooms);
//    }
//
//    @GetMapping("/chatRoomList/{chatRoomId}")
//    public ResponseEntity<?> getChatRoomDetail(@PathVariable Long chatRoomId) {
//        String currentUserEmail = authenticationService.getCurrentAuthenticatedUserEmail();
//        ChatRoomDetailsDTO chatRoomDetailsDTO = chatRoomService.getChatRoomDetail(chatRoomId, currentUserEmail);
//        if(chatRoomDetailsDTO == null) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Chat room not found or access denied.");
//        }
//        return ResponseEntity.ok(chatRoomDetailsDTO);
//    }
//
//    @GetMapping("/chatRoomList/{chatRoomId}/messages")
//    public ResponseEntity<List<MessageDTO>> getChatRoomMessages(@PathVariable Long chatRoomId) {
//        List<MessageDTO> messages = chatRoomService.getMessagesForChatRoom(chatRoomId);
//        return ResponseEntity.ok(messages);
//    }
//}