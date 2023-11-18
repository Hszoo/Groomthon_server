//package Goormoa.goormoa_server.controller.home;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//
//import java.util.List;
//
//@Controller
//@RequiredArgsConstructor
//public class HomeController {
//
//    private final MessageRepository messageRepository;
//    @GetMapping("/chat/{chatRoomId}")
//    public String chatPage(@PathVariable Long chatRoomId, Model model) {
//        List<Message> messages = messageRepository.findByChatRoom_ChatRoomId(chatRoomId);
//        model.addAttribute("messages",messages);
//        model.addAttribute("chatRoomId", chatRoomId);
//        return "chat"; // chat.html 템플릿 반환
//    }
//}
