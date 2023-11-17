package Goormoa.goormoa_server.repository.user;

import Goormoa.goormoa_server.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserEmail(String userEmail);
    Optional<User> findByUserId(Long UserId);
    @Query("SELECT COUNT(f) > 0 FROM Follow f WHERE f.toUser=:toUser AND f.fromUser=:fromUser")
    boolean existsFollowByToUserAndFromUser(User toUser, User fromUser);
}