package Goormoa.goormoa_server.repository.profile;

import Goormoa.goormoa_server.entity.profile.Profile;
import Goormoa.goormoa_server.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Profile findByUser(User user);
}