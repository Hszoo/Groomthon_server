package Goormoa.goormoa_server.service.group;

import Goormoa.goormoa_server.dto.group.GroupDTO;
import Goormoa.goormoa_server.entity.group.Group;
import Goormoa.goormoa_server.entity.group.GroupMember;
import Goormoa.goormoa_server.entity.user.User;
import Goormoa.goormoa_server.repository.group.GroupMemberRepository;
import Goormoa.goormoa_server.repository.group.GroupRepository;
import Goormoa.goormoa_server.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GroupService {
    private static final String ERROR = "error";
    private static final String SUCCESS = "success";
    private final GroupRepository groupRepository;
    private final GroupMemberRepository groupMemberRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    public String save(String currentUserEmail, GroupDTO groupDTO) {
        Optional<User> optionalUser = findUserEmail(currentUserEmail);
        if (optionalUser.isPresent()) {
            Group group = convertToEntity(groupDTO);
            group.setGroupHost(optionalUser.get());
            group.setCurrentParticipants(1);
            groupRepository.save(group);
            groupMemberRepository.save(new GroupMember(optionalUser.get(), group));
        }
        return SUCCESS;
    }

    public String update(String currentUserEmail, GroupDTO groupDTO) {
        Optional<User> optionalUser = findUserEmail(currentUserEmail);
        Optional<Group> optionalGroup = findGroup(groupDTO.getGroupId());

        User user = optionalUser.get();
        Group group = optionalGroup.get();
        if(!optionalUser.isPresent() || !optionalGroup.isPresent() || group.getGroupHost().getUserId() != user.getUserId()) {
            return "error";
        }
        group.setGroupTitle(groupDTO.getGroupTitle());
        group.setGroupInfo(groupDTO.getGroupInfo());
        group.setMaxParticipants(groupDTO.getMaxParticipants());
        group.setCategory(groupDTO.getCategory());
        group.setCloseDate(groupDTO.getCloseDate());
        groupRepository.save(group);
        return "success";
    }

    public String delete(String currentUserEmail, GroupDTO groupDTO) {
        Optional<User> optionalUser = findUserEmail(currentUserEmail);
        Optional<Group> optionalGroup = findGroup(groupDTO.getGroupId());

        User user = optionalUser.get();
        Group group = optionalGroup.get();
        if(!optionalUser.isPresent() || !optionalGroup.isPresent() || group.getGroupHost().getUserId() != user.getUserId()) {
            return "error";
        }
        groupRepository.delete(group);
        return "success";
    }

    private Group convertToEntity(GroupDTO groupDTO) {
        return modelMapper.map(groupDTO, Group.class);
    }

    private Optional<User> findUserEmail(String currentUserEmail) {
        return userRepository.findByUserEmail(currentUserEmail);
    }

    private Optional<Group> findGroup(Long groupId) {
        return groupRepository.findById(groupId);
    }

}