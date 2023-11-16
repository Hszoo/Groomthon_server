package Goormoa.goormoa_server.dto.group;

import Goormoa.goormoa_server.entity.category.Category;
import Goormoa.goormoa_server.dto.user.UserInfoDTO;
import Goormoa.goormoa_server.dto.user.UserDTO;
import Goormoa.goormoa_server.dto.profile.ProfileListDTO;
import Goormoa.goormoa_server.dto.profile.ProfileDetailDTO;


import Goormoa.goormoa_server.entity.group.Group;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GroupDTO {
    private Long groupId;
    private UserInfoDTO host;
    private Category category; // 카테고리
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date closeDate; // 그룹 종료 날짜
    private String groupTitle;
    private String groupInfo;
    // profileDTO 타입으로 하는 list로 수정 필요
    private ProfileListDTO participants;
    private ProfileListDTO applicants;
//    private List<ProfileDetailDTO> participants;
//    private List<ProfileDetailDTO> applicants; // host 에게만 보여지는 내용
    private Integer maxCount; // 그룹 최대 인원
    private Integer currentCount;
    private Boolean close;
    private UserInfoDTO userInfoDTO; // userInfoDTO 필드 추가


    public GroupDTO(Group group, UserInfoDTO hostInfo, List<ProfileDetailDTO> applicants, List<ProfileDetailDTO> participants) {
        this.groupId = group.getGroupId();
        this.host = hostInfo;
        this.closeDate = group.getCloseDate();
        this.groupTitle = group.getGroupTitle();
        this.groupInfo = group.getGroupInfo();
        this.maxCount = group.getMaxCount();
        this.currentCount = group.getCurrentCount();
//
//        this.applicants = applicants;
//        this.participants = participants;
    }

    public GroupDTO(Group group, UserInfoDTO userInfoDTO) {
        this.groupId = group.getGroupId();
        this.closeDate = group.getCloseDate();
        this.groupTitle = group.getGroupTitle();
        this.groupInfo = group.getGroupInfo();
        this.maxCount = group.getMaxCount();
        this.currentCount = group.getCurrentCount();
        this.userInfoDTO = userInfoDTO;
    }

    public GroupDTO(Group group, List<UserDTO> usersDTOs, UserInfoDTO hostInfo, List<ProfileDetailDTO> applicants, List<ProfileDetailDTO> participants) {
        this.groupId = group.getGroupId();
        this.host = hostInfo;
        this.closeDate = group.getCloseDate();
        this.groupTitle = group.getGroupTitle();
        this.groupInfo = group.getGroupInfo();
        this.maxCount = group.getMaxCount();
        this.currentCount = group.getCurrentCount();
//        this.applicants = applicants;
//        this.participants = participants;
    }
//
//    public List<ProfileDetailDTO> getGroupParticipants(List<Profile> participants) {
//        return participants.stream()
//                .map(ProfileDetailDTO::new)
//                .collect(Collectors.toList());
//    }
//
//    public List<ProfileDetailDTO> getGroupApplicants(List<Profile> applicants) {
//        return applicants.stream()
//                .map(ProfileDetailDTO::new)
//                .collect(Collectors.toList());
//    }
//    public List<GroupDTO> mapGroupsToDTOs(List<Group> groups) {
//        return groups.stream()
//                .map(group -> new GroupDTO(
//                        group,
//                        new UserInfoDTO(group.getGroupHost()),
//                        getGroupApplicants(group.getApplicants()),
//                        getGroupParticipants(group.getParticipants())))
//                .collect(Collectors.toList());
//    }

}
