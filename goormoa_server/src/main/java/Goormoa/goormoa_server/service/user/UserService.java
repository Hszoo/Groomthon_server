package Goormoa.goormoa_server.service.user;

import Goormoa.goormoa_server.dto.token.TokenDTO;
import Goormoa.goormoa_server.dto.user.UserDTO;
import Goormoa.goormoa_server.entity.user.User;
import Goormoa.goormoa_server.repository.user.UserRepository;
import Goormoa.goormoa_server.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private static final String ERROR = "error";
    private static final String SUCCESS = "success";

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    @Value("${jwt.secret}")
    private String secretKey;

    private final Long expiredMs = 1000 * 60 * 60L; // 1 hour

    public String save(UserDTO userDTO) {
        if (findUserEmail(userDTO.getUserEmail()).isPresent()) {
            return ERROR;
        }
        User user = convertToEntity(userDTO);
        user.setUserPassword(encodePassword(userDTO.getUserPassword()));
        userRepository.save(user);
        return SUCCESS;
    }

    public TokenDTO login(UserDTO userDTO) {
        return findUserEmail(userDTO.getUserEmail())
                .filter(user -> passwordEncoder.matches(userDTO.getUserPassword(), user.getUserPassword()))
                .map(user -> new TokenDTO(JwtUtil.createJwt(user.getUserEmail(), secretKey, expiredMs)))
                .orElse(null);
    }

    public String delete(String currentUserEmail) {
        return findUserEmail(currentUserEmail)
                .map(user -> {
                    userRepository.delete(user);
                    return SUCCESS;
                })
                .orElse(ERROR);
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
}
