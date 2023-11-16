package Goormoa.goormoa_server.dto.group;

import lombok.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DividedGroups {
    private List<GroupDTO> recruitingGroups;
    private List<GroupDTO> participatingGroups; // host 에게만 보여지는 내용
}
