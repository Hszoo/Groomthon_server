package Goormoa.goormoa_server.repository.follow;

import Goormoa.goormoa_server.entity.follow.Follow;
import Goormoa.goormoa_server.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {
    Optional<Follow> findByToUserAndFromUser(User targetUser, User currentUser);
    List<Follow> findByToUserUserId(Long userId);
    List<Follow> findByFromUserUserId(Long userId);
    User findByToUser(Long userId);
}

