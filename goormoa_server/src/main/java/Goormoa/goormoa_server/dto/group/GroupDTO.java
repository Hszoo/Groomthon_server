package Goormoa.goormoa_server.dto.group;

import Goormoa.goormoa_server.dto.profile.ProfileDetailDTO;
import Goormoa.goormoa_server.dto.user.UserInfoDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import Goormoa.goormoa_server.entity.group.Group;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class GroupDTO {
    private Long groupId;
    private UserInfoDTO groupHost;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date closeDate; // 그룹 종료 날짜
    private String groupTitle;
    private String groupInfo;
    // profileDTO 타입으로 하는 list로 수정 필요
    private List<ProfileDetailDTO> participants;
    private List<ProfileDetailDTO> applicants; // host 에게만 보여지는 내용
    private Integer maxCount; // 그룹 최대 인원
    private Integer currentCount;
    private Boolean close;

    private UserInfoDTO userInfoDTO;

    public GroupDTO(Group group, UserInfoDTO userInfoDTO) {
        this.groupId = group.getGroupId();
        this.closeDate = group.getCloseDate();
        this.groupTitle = group.getGroupTitle();
        this.groupInfo = group.getGroupInfo();
        this.maxCount = group.getMaxCount();
        this.currentCount = group.getCurrentCount();
        this.userInfoDTO = userInfoDTO;
    }
    public GroupDTO(Group group, UserInfoDTO hostInfo, List<ProfileDetailDTO> applicants, List<ProfileDetailDTO> participants) {
        this.groupId = group.getGroupId();
        this.groupHost = hostInfo;
        this.closeDate = group.getCloseDate();
        this.groupTitle = group.getGroupTitle();
        this.groupInfo = group.getGroupInfo();
        this.maxCount = group.getMaxCount();
        this.currentCount = group.getCurrentCount();

        this.applicants = applicants;
        this.participants = participants;
    }
}