package Goormoa.goormoa_server.repository.profile;

import Goormoa.goormoa_server.entity.profile.Profile;
import Goormoa.goormoa_server.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {
    // user 객체 구분자로 프로필 찾기
    Profile findByUser(User user);

    //Optional<Profile> findByUser(User user);
}