package Goormoa.goormoa_server.dto.group;

import Goormoa.goormoa_server.entity.group.Group;
import Goormoa.goormoa_server.entity.category.Category;
import Goormoa.goormoa_server.dto.profile.ProfileDetailDTO;
import Goormoa.goormoa_server.dto.user.UserInfoDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GroupDetailDTO {
    private Long groupId;
    private UserInfoDTO host;
//    private Category category; // 카테고리
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date closeDate; // 그룹 종료 날짜
    private String groupTitle;
    private String groupInfo;
    private List<ProfileDetailDTO> participants;
    private List<ProfileDetailDTO> applicants; // host 에게만 보여지는 내용
    private Integer maxCount; // 그룹 최대 인원
    private Integer currentCount;
    private Boolean close;


    public GroupDetailDTO(Group group, List<ProfileDetailDTO> applicants, List<ProfileDetailDTO> participants) {
        this.groupId = group.getGroupId();
        this.host = new UserInfoDTO(group.getGroupHost());
        this.closeDate = group.getCloseDate();
        this.groupTitle = group.getGroupTitle();
        this.groupInfo = group.getGroupInfo();
        this.maxCount = group.getMaxCount();
        this.currentCount = group.getCurrentCount();
        this.applicants = applicants;
        this.participants = participants;
    }
}
