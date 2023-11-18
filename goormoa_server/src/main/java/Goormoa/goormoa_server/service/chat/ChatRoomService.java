//package Goormoa.goormoa_server.service.chat;
//
//import Goormoa.goormoa_server.dto.chat.ChatRoomDTO;
//import Goormoa.goormoa_server.dto.chat.ChatRoomDetailsDTO;
//import Goormoa.goormoa_server.dto.chat.MessageDTO;
//import Goormoa.goormoa_server.dto.group.GroupMemberDTO;
//import Goormoa.goormoa_server.entity.chat.ChatRoom;
//import Goormoa.goormoa_server.entity.chat.Message;
//import Goormoa.goormoa_server.entity.group.Group;
//import Goormoa.goormoa_server.entity.group.GroupMember;
//import Goormoa.goormoa_server.entity.user.User;
//import Goormoa.goormoa_server.repository.chat.ChatRoomRepository;
//import Goormoa.goormoa_server.repository.chat.MessageRepository;
//import Goormoa.goormoa_server.repository.user.UserRepository;
//import lombok.RequiredArgsConstructor;
//import org.modelmapper.ModelMapper;
//import org.springframework.stereotype.Service;
//import javax.annotation.PostConstruct;
//import java.util.List;
//import java.util.NoSuchElementException;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@Service
//@RequiredArgsConstructor
//public class ChatRoomService {
//
//    private final UserRepository userRepository;
//    private final ChatRoomRepository chatRoomRepository;
//    private final MessageRepository messageRepository;
//    private final ModelMapper modelMapper;
//
//    public List<ChatRoomDTO> getChatRooms(String currentUserEmail) {
//        User currentUser = getUserByEmail(currentUserEmail);
//        List<ChatRoom> chatRooms = chatRoomRepository.findByUser(currentUser);
//        return chatRooms.stream()
//                .map(this::convertToChatRoomDTO)
//                .collect(Collectors.toList());
//    }
//    @PostConstruct
//    public void initModelMapper() {
//        modelMapper.createTypeMap(ChatRoom.class, ChatRoomDTO.class)
//                .addMappings(mapper -> mapper.map(ChatRoom::getGroup, ChatRoomDTO::setGroupDTO));
//    }
//    private ChatRoomDTO convertToChatRoomDTO(ChatRoom chatRoom) {
//
//        return modelMapper.map(chatRoom, ChatRoomDTO.class);
//    }
//
//    public ChatRoomDetailsDTO getChatRoomDetail(Long chatRoomId, String currentUserEmail) {
//        Optional<User> userOptional = userRepository.findByUserEmail(currentUserEmail);
//        Optional<ChatRoom> chatRoomOptional = chatRoomRepository.findById(chatRoomId);
//
//        if (chatRoomOptional.isPresent() && userOptional.isPresent()) {
//            ChatRoom chatRoom = chatRoomOptional.get();
//            Group group = chatRoom.getGroup();
//
//            boolean isMember = group.getGroupMembers().stream()
//                    .anyMatch(groupMember -> groupMember.getUser().getUserEmail().equals(currentUserEmail));
//
//            if (!isMember) {
//                return null;
//            }
//
//            ChatRoomDTO chatRoomDTO = convertToChatRoomDTO(chatRoom);
//            List<GroupMember> groupMembers = group.getGroupMembers();
//            List<GroupMemberDTO> groupMemberDTOs = groupMembers.stream()
//                    .map(groupMember -> convertToGroupMemberDTO(groupMember))
//                    .collect(Collectors.toList());
//
//            ChatRoomDetailsDTO chatRoomDetailsDTO = new ChatRoomDetailsDTO();
//            chatRoomDetailsDTO.setChatRoomDTO(chatRoomDTO);
//            chatRoomDetailsDTO.setGroupMembers(groupMemberDTOs);
//
//            return chatRoomDetailsDTO;
//        } else {
//            // 채팅방을 찾을 수 없는 경우, 적절한 예외 처리
//            return null;
//        }
//    }
//
//    public boolean isMemberOfChatRoom(Long chatRoomId, String currentUserEmail) {
//        Optional<User> userOptional = userRepository.findByUserEmail(currentUserEmail);
//        Optional<ChatRoom> chatRoomOptional = chatRoomRepository.findById(chatRoomId);
//        if (chatRoomOptional.isPresent() && userOptional.isPresent()) {
//            ChatRoom chatRoom = chatRoomOptional.get();
//            Group group = chatRoom.getGroup();
//
//            boolean isMember = group.getGroupMembers().stream()
//                    .anyMatch(groupMember -> groupMember.getUser().getUserEmail().equals(currentUserEmail));
//
//            if (!isMember) {
//                // 현재 사용자가 채팅방의 멤버가 아닐 경우, 예외 처리 또는 에러 메시지 반환
//                return false;
//            }
//        }
//        return true;
//    }
//
//    public void saveMessage(Long chatRoomId, MessageDTO messageDTO) {
//        Optional<ChatRoom> chatRoomOptional = chatRoomRepository.findById(chatRoomId);
//        System.out.println("[saveMessage]");
//        if (chatRoomOptional.isPresent()) {
//            System.out.println("[saveMessage] isPresent");
//            ChatRoom chatRoom = chatRoomOptional.get();
//            messageDTO.setChatRoomId(chatRoomId);
//
//            // MessageDTO를 Message 엔티티로 변환
//            Message message = convertToMessageEntity(messageDTO);
//            message.setChatRoom(chatRoom); // ChatRoom 설정
//
//            // Message 엔티티 저장
//            messageRepository.save(message);
//        }
//        System.out.println("[saveMessage] !!isPresent");
//    }
//    public List<MessageDTO> getMessagesForChatRoom(Long chatRoomId) {
//        List<Message> messages = messageRepository.findByChatRoom_ChatRoomId(chatRoomId);
//        // Message 엔티티 객체들을 MessageDTO 객체들로 변환합니다.
//        return messages.stream()
//                .map(this::convertToMessageDTO)
//                .collect(Collectors.toList());
//
//    }
//    private Message convertToMessageEntity(MessageDTO messageDTO) {
//        return modelMapper.map(messageDTO, Message.class);
//    }
//    private MessageDTO convertToMessageDTO(Message message) {
//        if (message == null) {
//            return null;
//        }
//        // ModelMapper를 사용하여 Message 객체를 MessageDTO 객체로 매핑합니다.
//        return modelMapper.map(message, MessageDTO.class);
//    }
//
//
//    private GroupMemberDTO convertToGroupMemberDTO(GroupMember groupMember) {
//        return modelMapper.map(groupMember, GroupMemberDTO.class);
//    }
//
//    private User getUserByEmail(String userEmail) {
//        return userRepository.findByUserEmail(userEmail)
//                .orElseThrow(() -> new NoSuchElementException("User not found with email: " + userEmail));
//    }
//}
