package Goormoa.goormoa_server.dto.user;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDTO {
    private Long userId;
    private String userName;
    private String userEmail;
    private String userPassword;
}
