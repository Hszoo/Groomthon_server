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
    private final GroupRepository groupRepository;
    private final GroupMemberRepository groupMemberRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public String save(String currentUserEmail, GroupDTO groupDTO) {
        User user = getByUser(currentUserEmail);
        if (user != null) {
            Group group = convertToEntity(groupDTO);
            group.setGroupHost(user);
            group.setCurrentParticipants(1);
            groupRepository.save(group);
            groupMemberRepository.save(new GroupMember(user, group));
            return "그룹생성이 완료되었습니다.";
        }
        return "error";
    }

    public String update(String currentUserEmail, GroupDTO groupDTO) {
        User user = getByUser(currentUserEmail);
        Group group = getByGroup(groupDTO);
        if (user != null && group != null && group.getGroupHost().getUserId().equals(user.getUserId())) {
            updateGroupDetails(group, groupDTO);
            groupRepository.save(group);
            return "그룹 업데이트가 완료되었습니다.";
        }
        return "error";
    }

    public String delete(String currentUserEmail, GroupDTO groupDTO) {
        User user = getByUser(currentUserEmail);
        Group group = getByGroup(groupDTO);
        if (user != null && group != null && group.getGroupHost().getUserId().equals(user.getUserId())) {
            groupRepository.delete(group);
            return "그룹삭제가 완료되었습니다.";
        }
        return "error";
    }

    private void updateGroupDetails(Group group, GroupDTO groupDTO) {
        group.setGroupTitle(groupDTO.getGroupTitle());
        group.setGroupInfo(groupDTO.getGroupInfo());
        group.setMaxParticipants(groupDTO.getMaxParticipants());
        group.setCategory(groupDTO.getCategory());
        group.setCloseDate(groupDTO.getCloseDate());
    }

    private Group convertToEntity(GroupDTO groupDTO) {
        return modelMapper.map(groupDTO, Group.class);
    }
    private User getByUser(String currentUserEmail) {
        return userRepository.findByUserEmail(currentUserEmail).orElse(null);
    }
    private Group getByGroup(GroupDTO groupDTO) {
        return groupRepository.findById(groupDTO.getGroupId()).orElse(null);
    }
}
