package Goormoa.goormoa_server.service.group;

import Goormoa.goormoa_server.dto.alarm.GroupAlarmDTO;
import Goormoa.goormoa_server.dto.group.DividedGroups;
import Goormoa.goormoa_server.dto.group.GroupDetailDTO;
import Goormoa.goormoa_server.dto.profile.ProfileDetailDTO;
import Goormoa.goormoa_server.dto.user.UserInfoDTO;
import Goormoa.goormoa_server.entity.profile.Profile;
import Goormoa.goormoa_server.dto.group.GroupDTO;
import Goormoa.goormoa_server.entity.group.Group;
import Goormoa.goormoa_server.entity.group.GroupMember;
import Goormoa.goormoa_server.entity.user.User;
import Goormoa.goormoa_server.repository.follow.FollowRepository;
import Goormoa.goormoa_server.repository.group.GroupMemberRepository;
import Goormoa.goormoa_server.repository.group.GroupRepository;
import Goormoa.goormoa_server.repository.profile.ProfileRepository;
import Goormoa.goormoa_server.repository.user.UserRepository;
import Goormoa.goormoa_server.service.alarm.AlarmService;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Transactional
@Service
@RequiredArgsConstructor
public class GroupService {
    private final GroupRepository groupRepository;
    private final ProfileRepository profileRepository;
    private final GroupMemberRepository groupMemberRepository;
    private final UserRepository userRepository;
    private final FollowRepository followRepository;
    private final AlarmService alarmService;
    private final ModelMapper modelMapper;

    private static final String SUCCESS = "success";
    private static final String ERROR = "error";
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @PersistenceContext
    private EntityManager entityManager;
//    public boolean checkFollowRelationship(User toUser, User fromUser) {
//        String jpql = "SELECT COUNT(f) > 0 FROM Follow f WHERE f.toUser=:toUser AND f.fromUser=:fromUser";
//        Query query = entityManager.createQuery(jpql);
//        query.setParameter("toUser", toUser);
//        query.setParameter("fromUser", fromUser);
//        return (boolean) query.getSingleResult();
//    }

    /* 전체 모임 조회
        -> 팔로우하는 사용자가 모집 중인 모임 조회  */
    public List<GroupDTO> getAllGroups(String userEmail) {
        // 현재 사용자 정보를 가져오기
        User currentUser = userRepository.findByUserEmail(userEmail).orElse(null);
        if (currentUser == null) {
            return Collections.emptyList();
        }
        // 현재 모집 중인 모든 그룹
        List<Group> allGroups = groupRepository.findByCloseFalse();
        if (allGroups == null) {
            return Collections.emptyList();
        }
        // 사용자가 팔로우하는 사용자가 모집 중인 모임
        List<GroupDTO> followingGroupDTOs = Objects.requireNonNull(allGroups).stream()
                .filter(group -> userRepository.existsFollowByToUserAndFromUser(currentUser, group.getGroupHost()))
                .map(this::mapGroupToDTO)
                .collect(Collectors.toList());
        return mapGroupsToDTOs(followingGroupDTOs);
    }

    /* 내가 포함된 모든 모임 조회 */
    public DividedGroups getMyGroups(String userEmail) {
        Optional<User> optionalUser = userRepository.findByUserEmail(userEmail);
        if (optionalUser.isEmpty()) {
            return null;
        }
        User user = optionalUser.get(); // 호스트 조회 : 사용자 객체 사용
        Profile userProfile = profileRepository.findByUser(user); // 참가자 조회 : 프로필 사용
        return mapToDividedGroups(groupRepository.debugFindByGroupHost(user), groupRepository.debugFindByParticipantsContaining(userProfile));
    }

    public String update(String currentUserEmail, GroupDTO groupDTO) {
        User user = getByUser(currentUserEmail);
        Group group = getByGroup(groupDTO);
        if (user != null && group != null && group.getGroupHost().getUserId().equals(user.getUserId())) {
            updateGroupDetails(group, groupDTO);
            groupRepository.save(group);
            return "그룹 업데이트가 완료되었습니다.";
        }
        return ERROR;
    }

    public String delete(String currentUserEmail, GroupDTO groupDTO) {
        User user = getByUser(currentUserEmail);
        Group group = getByGroup(groupDTO);
        if (user != null && group != null && group.getGroupHost().getUserId().equals(user.getUserId())) {
            groupRepository.delete(group);
            return "그룹삭제가 완료되었습니다.";
        }
        return ERROR;
    }

    private void updateGroupDetails(Group group, GroupDTO groupDTO) {
        group.setGroupTitle(groupDTO.getGroupTitle());
        group.setGroupInfo(groupDTO.getGroupInfo());
        group.setMaxCount(groupDTO.getMaxCount());
        group.setCategory(groupDTO.getCategory());
        group.setCloseDate(groupDTO.getCloseDate());
    }

    public String save(String currentUserEmail,GroupDTO groupDTO) {
        Optional<User> optionalUser = findUserEmail(currentUserEmail);
        /* 사용자 존재 하는 경우 */
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            Group group = convertToEntity(groupDTO);
            group.setGroupHost(user);
            group.setCurrentCount(1);
            group.setGroupInfo(group.getGroupInfo());
            group.setMaxCount(groupDTO.getMaxCount());
            group.setCategory(groupDTO.getCategory());
            group.setCloseDate(groupDTO.getCloseDate());
            group.setClose(false);
            groupRepository.save(group);
            groupMemberRepository.save(new GroupMember(optionalUser.get(), group));
        }
        return SUCCESS;
    }

    /* 모임 상세 페이지 조회 */
    public GroupDTO detailGroup(Long groupId) {
        Group group = groupRepository.findByGroupId(groupId);
        return new GroupDTO(group);
    }

    private Group convertToEntity(GroupDTO groupDTO) {
        return modelMapper.map(groupDTO, Group.class);
    }

    private Optional<User> findUserEmail(String currentUserEmail) {
        return userRepository.findByUserEmail(currentUserEmail);
    }

//    private Optional<Group> findGroup(Long groupId) {
//        return groupRepository.findById(groupId);
//    }

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

        GroupAlarmDTO groupAlarmDTO = new GroupAlarmDTO();
        groupAlarmDTO.setGroupId(groupDto.getGroupId());

        Group group = groupRepository.findById(groupDto.getGroupId()).orElse(null);
        if (group != null) {
            List<Profile> participants = group.getParticipants();
            for (Profile participant : participants) {
                alarmService.saveFinishAlarm(participant.getUser().getUserEmail(), groupDto);
            }
        }
    }

    // 모임 신청 요청 처리
    @Transactional
    public String applyToGroup(Long groupId, String userEmail) {
        Group group = groupRepository.findById(groupId).orElse(null);
        Optional<User> optionalUser = userRepository.findByUserEmail(userEmail);

        if (group == null || optionalUser.isEmpty()) {
            return null;  // groupId에 해당하는 그룹이 없거나 사용자가 존재하지 않는 경우
        }

        User user = optionalUser.get();
        Profile applicant = profileRepository.findByUser(user);

        // 이미 그룹에 참여 중인지 확인
        if (group.getParticipants() != null && group.getParticipants().contains(applicant)) {
            return  ERROR + "already participate";
        }

        // 이미 그룹에 지원자로 등록되어 있는지 확인
        if (group.getApplicants() != null && !group.getApplicants().contains(applicant)) {
            group.addApplicant(applicant);
            groupRepository.save(group);
            // 모임의 호스트에게 알림 보내기
            alarmService.saveGroupAlarm(group.getGroupHost(), user, group);
            return SUCCESS;
        } else {
            // 이미 지원자로 등록되어 있으면 실패 메시지 반환
            return ERROR + "already apply";
        }
    }

    @Transactional
    public String approveApplication(Long groupId, Long userId) {
        Optional<Group> groupOptional = groupRepository.findById(groupId);
        Optional<User> userOptional = userRepository.findById(userId);

        if (groupOptional.isPresent() && userOptional.isPresent()) {
            Group group = groupOptional.get();
            User user = userOptional.get();

            // 해당 사용자에 대한 프로필을 가져옴
            Optional<Profile> applicantOptional = Optional.ofNullable(profileRepository.findByUser(user));

            if (applicantOptional.isPresent()) {
                Profile applicant = applicantOptional.get();

                // 모임 내 신청자에 포함되어 있는지 확인
                if (group.getApplicants() != null && group.getApplicants().contains(applicant) && !group.getParticipants().contains(applicant)) {
                    group.acceptApplicant(applicant);
                    applicant.getParticipatingGroups().add(group);
                    groupRepository.save(group);
                    alarmService.saveAcceptAlarm(group, applicant.getUser());
                    return SUCCESS;
                } else {
                    // 신청자가 아닌 경우
                    return ERROR + "this user is not applicant";
                }
            } else {
                // 해당 사용자에 대한 프로필이 없으면 실패 메시지 반환
                return ERROR + "profile not found for user";
            }
        }
        return ERROR + "user approve fail";
    }

    @Transactional
    public String rejectApplication(Long groupId, Long userId) {
        Optional<Group> groupOptional = groupRepository.findById(groupId);
        Optional<User> userOptional = userRepository.findById(userId);

        if (groupOptional.isPresent() && userOptional.isPresent()) {
            Group group = groupOptional.get();
            User user = userOptional.get();

            // 해당 사용자에 대한 프로필을 가져옴
            Optional<Profile> applicantOptional = Optional.ofNullable(profileRepository.findByUser(user));

            if (applicantOptional.isPresent()) {
                Profile applicant = applicantOptional.get();

                // 모임 내 신청자에 포함되어 있는지 확인
                if (group.getApplicants() != null && group.getApplicants().contains(applicant)) {
                    group.removeApplicant(applicant);
                    groupRepository.save(group);
                    alarmService.saveRejectAlarm(group, applicant.getUser());
                    return SUCCESS;
                } else {
                    // 신청자가 아닌 경우
                    return ERROR +"this user is not applicant";
                }
            } else {
                // 해당 사용자에 대한 프로필이 없으면 실패 메시지 반환
                return ERROR +"profile not found for user";
            }
        }
        return ERROR + "user reject fail";

    }

    // -> DTO 전환 위한 메서드
    private List<GroupDTO> mapGroupsToDTOs(List<GroupDTO> groups) {
        return groups.stream()
                .map(group -> modelMapper.map(group, GroupDTO.class))
                .collect(Collectors.toList());
    }

//    private List<ProfileDetailDTO> mapProfilesToDTOs(List<Profile> profiles) {
//        return profiles.stream()
//                .map(profile -> modelMapper.map(profile, ProfileDetailDTO.class))
//                .collect(Collectors.toList());
//    }

    private GroupDTO mapGroupToDTO(Group group) {
        return new GroupDTO(group, group.getGroupHost());
    }

    /* 참여한 모임 조회 결과 dto 전환 */
    private DividedGroups mapToDividedGroups(List<Group> recruitingGroups, List<Group> participatingGroups) {
        List<GroupDTO> myRecruitingGroupDTOs = new ArrayList<>();
        for (Group group : recruitingGroups) {
            Hibernate.initialize(group.getGroupHost());
            myRecruitingGroupDTOs.add(new GroupDTO(group, group.getGroupHost()));
        }

        List<GroupDTO> myParticipatingGroupDTOs = new ArrayList<>();
        for (Group group : participatingGroups) {
            myParticipatingGroupDTOs.add(new GroupDTO(group, group.getGroupHost()));
        }

        return new DividedGroups(myRecruitingGroupDTOs, myParticipatingGroupDTOs);
    }

//    private GroupDetailDTO mapGroupDetailDTO(Group group) {
//        // 컬렉션 초기화
//        Hibernate.initialize(group.getApplicants());
//        Hibernate.initialize(group.getParticipants());
//
//        // 초기화된 컬렉션을 사용하여 DTO 생성
//        List<ProfileDetailDTO> applicants = mapProfilesToDTOs(group.getApplicants());
//        List<ProfileDetailDTO> participants = mapProfilesToDTOs(group.getParticipants());
//
//        return new GroupDetailDTO(group, applicants, participants);
//    }
    private User getByUser(String currentUserEmail) {
        return userRepository.findByUserEmail(currentUserEmail).orElse(null);
    }
    private Group getByGroup(GroupDTO groupDTO) {
        return groupRepository.findById(groupDTO.getGroupId()).orElse(null);
    }
}
