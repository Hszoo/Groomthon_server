package Goormoa.goormoa_server.service.user;

import Goormoa.goormoa_server.dto.token.TokenDTO;
import Goormoa.goormoa_server.dto.user.UserDTO;
import Goormoa.goormoa_server.dto.user.UserDetailDTO;
import Goormoa.goormoa_server.entity.user.User;
import Goormoa.goormoa_server.repository.user.UserRepository;
import Goormoa.goormoa_server.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    @Value("${jwt.secret}")
    private String secretKey;
    private final Long expiredMs = 1000 * 60 * 60L; // 1 hour

    /* 전체 사용자 조회 */
    public List<UserDetailDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return mapUserDetailsToDTOs(users);
    }

    public String save(UserDTO userDTO) {
        if (userRepository.findByUserEmail(userDTO.getUserEmail()).isPresent()) {
            return "존재하는 이메일 입니다.";
        }
        User user = convertToEntity(userDTO);
        user.setUserPassword(encodePassword(userDTO.getUserPassword()));
        userRepository.save(user);
        return "회원가입이 완료되었습니다.";
    }

    public TokenDTO login(UserDTO userDTO) {
        Optional<User> userOptional = userRepository.findByUserEmail(userDTO.getUserEmail());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (passwordEncoder.matches(userDTO.getUserPassword(), user.getUserPassword())) {
                return new TokenDTO(JwtUtil.createJwt(user.getUserEmail(), secretKey, expiredMs));
            }
        }
        return null;
    }

    public String delete(String currentUserEmail) {
        Optional<User> userOptional = userRepository.findByUserEmail(currentUserEmail);
        if (userOptional.isPresent()) {
            userRepository.delete(userOptional.get());
            return "회원탈퇴가 완료되었습니다.";
        }
        return "회원탈퇴가 실패했습니다.";
    }
    public Optional<User> findUserEmail(String currentUserEmail) {
        return userRepository.findByUserEmail(currentUserEmail);
    }

    private User convertToEntity(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }

    private String encodePassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    private List<UserDetailDTO> mapUserDetailsToDTOs(List<User> users) {
        return users.stream()
                .map(user -> new UserDetailDTO(user))
                .collect(Collectors.toList());
    }
}
