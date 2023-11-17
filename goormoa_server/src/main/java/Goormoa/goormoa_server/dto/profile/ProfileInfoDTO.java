package Goormoa.goormoa_server.dto.profile;

import Goormoa.goormoa_server.entity.category.Category;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProfileInfoDTO {
    private String profileImg; // 프로필 사진
    private List<String> category;
}
