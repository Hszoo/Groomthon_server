package Goormoa.goormoa_server.service.group;

import Goormoa.goormoa_server.dto.group.DividedGroups;
import Goormoa.goormoa_server.dto.profile.ProfileDetailDTO;
import Goormoa.goormoa_server.dto.user.UserInfoDTO;
import Goormoa.goormoa_server.entity.profile.Profile;
import Goormoa.goormoa_server.dto.group.GroupDTO;
import Goormoa.goormoa_server.entity.group.Group;
import Goormoa.goormoa_server.entity.group.GroupMember;
import Goormoa.goormoa_server.entity.user.User;
import Goormoa.goormoa_server.repository.group.GroupMemberRepository;
import Goormoa.goormoa_server.repository.group.GroupRepository;
import Goormoa.goormoa_server.repository.profile.ProfileRepository;
import Goormoa.goormoa_server.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GroupService {
    private final GroupRepository groupRepository;
    private final ProfileRepository profileRepository;
    private final GroupMemberRepository groupMemberRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public List<GroupDTO> getAllGroups() {
        List<Group> groups = groupRepository.findAll();
        // 이 그룹에서 팔로우 중인 사용자의 그룹만 나오도록 필터링
        return mapGroupsToDTOs(groups);
    }

    /* 모집 중인 모임 조회 */
    public List<GroupDTO> getRecruitingGroups() {
        List<Group> recruitingGroups = groupRepository.findByCloseFalse();
        // 이 그룹에서 팔로우 중인 사용자의 그룹만 나오도록 필터링

        return mapGroupsToDTOs(recruitingGroups);
    }

    /* 내가 포함된 모든 모임 조회 */

    public String save(String currentUserEmail, GroupDTO groupDTO) {
        User user = getByUser(currentUserEmail);
        if (user != null) {
            Group group = convertToEntity(groupDTO);
            group.setGroupHost(user);
            group.setCurrentCount(1);
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
        group.setMaxCount(groupDTO.getMaxCount());
        group.setCategory(groupDTO.getCategory());
        group.setCloseDate(groupDTO.getCloseDate());
    }


    /* 모임 상세 페이지 조회 */
    public GroupDTO detailGroup(String currentUserEmail, Long groupId) {
        Optional<User> optionalUser = userRepository.findByUserEmail(currentUserEmail);
        if (optionalUser.isEmpty()) {
            return null;
        }
        Group group = groupRepository.findByGroupId(groupId);

        return mapGroupToDTO(group);
    }

    public DividedGroups getMyGroups(String userEmail) {
        Optional<User> optionalUser = userRepository.findByUserEmail(userEmail);
        if (optionalUser.isEmpty()) {
            return null;
        }
        User user = optionalUser.get();
        Profile userProfile = profileRepository.findByUser(user);

        List<Group> recruitingGroups = groupRepository.debugFindByGroupHost(user);
        List<Group> participatingGroups = groupRepository.debugFindByParticipantsContaining(userProfile);

        List<GroupDTO> myRecruitingGroupDTOs = new ArrayList<>();
//        for (Group group : recruitingGroups) {
//            Hibernate.initialize(group.getGroupHost());
//            GroupDTO groupDTO = new GroupDTO(group, new UserInfoDTO(group.getGroupHost()));
//            myRecruitingGroupDTOs.add(groupDTO);
//        }
        // 모집중인 모임 목록
        for (Group group : recruitingGroups) {
            List<ProfileDetailDTO> participantsDTOs = group.getParticipants().stream()
                    .map(ProfileDetailDTO::new)
                    .collect(Collectors.toList());
            List<ProfileDetailDTO> applicantsDTOs = group.getApplicants().stream()
                    .map(ProfileDetailDTO::new)
                    .collect(Collectors.toList());
            // 그룹 내 참여자, 신청자 사용자 하나하나에 대한 userInfo 설정이 필요하다.
//
//            List<UserDTO> groupUsersDTOs = group.getParticipants().stream()
//                    .map(UserDTO::new)
//                    .collect(Collectors.toList());

//            myRecruitingGroupDTOs.add(new GroupDTO(group, groupUsersDTOs, new UserInfoDTO(group.getGroupHost()), applicantsDTOs, participantsDTOs));
            myRecruitingGroupDTOs.add(new GroupDTO(group, new UserInfoDTO(group.getGroupHost()), applicantsDTOs, participantsDTOs));
        }


        // 참여한 모임 목록
        List<GroupDTO> myParticipatingGroupDTOs = new ArrayList<>();
        for (Group group : participatingGroups) {
            List<ProfileDetailDTO> participantsDTOs = group.getParticipants().stream()
                    .map(ProfileDetailDTO::new)
                    .collect(Collectors.toList());
            List<ProfileDetailDTO> applicantsDTOs = group.getApplicants().stream()
                    .map(ProfileDetailDTO::new)
                    .collect(Collectors.toList());
            myParticipatingGroupDTOs.add(new GroupDTO(group, new UserInfoDTO(group.getGroupHost()), applicantsDTOs, participantsDTOs));
        }

        return new DividedGroups(myRecruitingGroupDTOs, myParticipatingGroupDTOs);
    }

    private Optional<User> findUserEmail(String currentUserEmail) {
        return userRepository.findByUserEmail(currentUserEmail);
    }

    private Optional<Group> findGroup(Long groupId) {
        return groupRepository.findById(groupId);
    }

    // 현재 사용자가 host인지 판별하는 로직 작성
    public Boolean isHost(String loginUserEmail, Long groupId) {
        // 그룹을 가져올 때 FetchType.LAZY로 설정되어 있으므로 Hibernate.initialize 사용
        Group group = groupRepository.findByGroupId(groupId);
        Hibernate.initialize(group.getGroupHost());

        // 호스트의 이메일과 로그인한 사용자의 이메일 비교
        return loginUserEmail.equals(group.getGroupHost().getUserEmail());
    }

    // 모임 모집 마감 처리
    public void closeGroup(String loginUserEmail, GroupDTO groupDto)  {
        if(this.isHost(loginUserEmail, groupDto.getGroupId()))
            groupDto.setClose(true);
    }

    /* 모임 신청 요청 처리 */
    @Transactional
    public String applyToGroup(Long groupId, String userEmail) {
        Group group = groupRepository.findById(groupId).orElse(null);
        Optional<User> optionalUser = userRepository.findByUserEmail(userEmail);

        if (optionalUser.isEmpty()) {
            return null;
        }
        User user = optionalUser.get();
        Profile applicant = profileRepository.findByUser(user);

        if (applicant == null) {
            return null;
        }

        // 이미 그룹에 지원자로 등록되어 있는지 확인
        if (!group.getApplicants().contains(applicant)) {
            group.addApplicant(applicant);
            applicant.getParticipatingGroups().add(group);
            groupRepository.save(group);
            return "success";
        } else {
            // 이미 지원자로 등록되어 있으면 실패 메시지 반환
            return "applicant already exists";
        }
    }

    @Transactional
    public void acceptApplication(Long groupId, Long userId) {
        Group group = groupRepository.findById(groupId).orElse(null);
        Profile applicant = profileRepository.findById(userId).orElse(null);

        if (group != null && applicant != null) {
            group.acceptApplicant(applicant);
            applicant.getParticipatingGroups().add(group);
            groupRepository.save(group);
        }
    }

    @Transactional
    public void rejectApplication(Long groupId, Long userId) {
        Group group = groupRepository.findById(groupId).orElse(null);
        Profile applicant = profileRepository.findById(userId).orElse(null);

        if (group != null && applicant != null) {
            group.rejectApplicant(applicant);
            groupRepository.save(group);
        }
    }

    // -> DTO 전환 위한 메서드
    private List<GroupDTO> mapGroupsToDTOs(List<Group> groups) {
        return groups.stream()
                .map(group -> modelMapper.map(group, GroupDTO.class))
                .collect(Collectors.toList());
    }

    //    private List<GroupDTO> mapGroupsToDTOs(List<Group> groups) {
//        return groups.stream()
//                .map(group -> mapGroupToDTO(group)
//                        .collect(Collectors.toList());
//    }
    private List<ProfileDetailDTO> mapProfilesToDTOs(List<Profile> profiles) {
        return profiles.stream()
                .map(profile -> modelMapper.map(profile, ProfileDetailDTO.class))
                .collect(Collectors.toList());
    }

    private GroupDTO mapGroupToDTO(Group group) {
        UserInfoDTO hostInfo = new UserInfoDTO(group.getGroupHost());
        // group.getApplicants() 및 group.getParticipants()를 DTO로 변환
        List<ProfileDetailDTO> applicants = mapProfilesToDTOs(group.getApplicants());
        List<ProfileDetailDTO> participants = mapProfilesToDTOs(group.getParticipants());

        return new GroupDTO(group, hostInfo, applicants, participants);
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
