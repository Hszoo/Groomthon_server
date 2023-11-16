package Goormoa.goormoa_server.dto.group;

import Goormoa.goormoa_server.entity.category.Category;
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
    private String groupTitle;
    private String groupInfo;
    private Integer maxParticipants; // 그룹 최대 인원
    private Category category; // 카테고리
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date closeDate; // 그룹 종료 날짜
}
