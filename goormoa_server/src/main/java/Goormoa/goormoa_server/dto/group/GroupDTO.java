package Goormoa.goormoa_server.dto.group;

import Goormoa.goormoa_server.entity.category.Category;
import Goormoa.goormoa_server.dto.user.UserInfoDTO;

import Goormoa.goormoa_server.entity.group.Group;
import Goormoa.goormoa_server.entity.user.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GroupDTO {
    private Long groupId;
    private Long hostId;
    private String hostEmail;
    private String hostName;
    private String category;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date closeDate;
    private String groupTitle;
    private String groupInfo;
    private Integer maxCount;
    private Integer currentCount;
    private Boolean close;

    public GroupDTO(Group group, User user) {
        this.groupId = group.getGroupId();
        this.closeDate = group.getCloseDate();
        this.hostId = user.getUserId();
        this.hostEmail = user.getUserEmail();
        this.hostName = user.getUserName();
        this.groupTitle = group.getGroupTitle();
        this.groupInfo = group.getGroupInfo();
        this.maxCount = group.getMaxCount();
        this.currentCount = group.getCurrentCount();
        this.category = group.getCategory();
        this.close = group.getClose();
    }
    public GroupDTO(Group group) {
        this.groupId = group.getGroupId();
        this.hostId = group.getGroupHost().getUserId();
        this.hostName = group.getGroupHost().getUserName();
        this.hostEmail = group.getGroupHost().getUserEmail();
        this.category = group.getCategory();
        this.closeDate = group.getCloseDate();
        this.groupTitle = group.getGroupTitle();
        this.groupInfo = group.getGroupInfo();
        this.maxCount = group.getMaxCount();
        this.currentCount = group.getCurrentCount();
        this.close = group.getClose();
    }
}
