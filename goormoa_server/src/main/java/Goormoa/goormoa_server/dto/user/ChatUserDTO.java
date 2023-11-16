package Goormoa.goormoa_server.dto.user;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ChatUserDTO {
    private Long userId;
    private String userName;
}
