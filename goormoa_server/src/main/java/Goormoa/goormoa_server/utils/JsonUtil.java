package Goormoa.goormoa_server.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import Goormoa.goormoa_server.dto.profile.ProfileDTO;
import Goormoa.goormoa_server.dto.user.UserInfoDTO;


public class JsonUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String convertProfileAndUserInfoToJson(ProfileDTO profileDTO, UserInfoDTO userInfoDTO) {
        try {
            String profileJson = objectMapper.writeValueAsString(profileDTO);
            String userInfoJson = objectMapper.writeValueAsString(userInfoDTO);

            // 여기서 커스텀 로직을 적용하여 두 JSON을 합칠 수 있습니다.
            // 이 예시에서는 단순히 합쳐서 문자열로 반환합니다.
            return profileJson + userInfoJson;
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting objects to JSON", e);
        }
    }
}
